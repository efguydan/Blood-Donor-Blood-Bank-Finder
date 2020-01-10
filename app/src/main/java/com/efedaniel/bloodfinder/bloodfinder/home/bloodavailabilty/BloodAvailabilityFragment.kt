package com.efedaniel.bloodfinder.bloodfinder.home.bloodavailabilty

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.bottomsheets.BottomSheet
import com.afollestad.materialdialogs.customview.customView
import com.efedaniel.bloodfinder.App

import com.efedaniel.bloodfinder.R
import com.efedaniel.bloodfinder.base.BaseFragment
import com.efedaniel.bloodfinder.base.BaseViewModel
import com.efedaniel.bloodfinder.bloodfinder.models.request.UploadBloodAvailabilityRequest
import com.efedaniel.bloodfinder.bloodfinder.models.request.UserDetails
import com.efedaniel.bloodfinder.bloodfinder.reusables.SpinnerAdapter
import com.efedaniel.bloodfinder.databinding.FragmentBloodAvailabiltyBinding
import com.efedaniel.bloodfinder.extensions.hide
import com.efedaniel.bloodfinder.extensions.onScrollChanged
import com.efedaniel.bloodfinder.extensions.registerTextViewLabel
import com.efedaniel.bloodfinder.utils.Data
import com.efedaniel.bloodfinder.utils.PrefKeys
import com.efedaniel.bloodfinder.utils.PrefsUtils
import kotlinx.android.synthetic.main.bottomsheet_upload_blood_availability.*
import javax.inject.Inject

class BloodAvailabilityFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var prefsUtils: PrefsUtils

    private lateinit var binding: FragmentBloodAvailabiltyBinding
    private lateinit var viewModel: BloodAvailabilityViewModel
    private lateinit var user: UserDetails
    private var currentPosition = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBloodAvailabiltyBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpToolbar()
        (mainActivity.applicationContext as App).component.inject(this)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(BloodAvailabilityViewModel::class.java)
        binding.viewModel = viewModel
        viewModel.getUserBloodAvailability()
        binding.addAvailabilityFab.setOnClickListener { setupNewEntryDialog() }
        binding.postingsRecyclerView.adapter = BloodAvailabilityAdapter { id: String, pos: Int ->
            currentPosition = pos
            showDialogWithAction(
                title = getString(R.string.delete_posting),
                body = getString(R.string.are_you_sure_you_want_to_delete),
                positiveRes = R.string.delete,
                negativeRes = R.string.cancel,
                positiveAction = {
                    viewModel.deleteBloodAvailability(id)
                    (binding.postingsRecyclerView.findViewHolderForLayoutPosition(pos) as
                            BloodAvailabilityAdapter.ViewHolder).triggerDeletingProgress(true)
                }
            )
        }
        binding.parentLayout.onScrollChanged { mainActivity.invalidateToolbarElevation(it) }
        observe()
    }

    private fun setupNewEntryDialog() {
        MaterialDialog(mainActivity, BottomSheet()).show {
            title(R.string.upload_blood_availability)
            customView(R.layout.bottomsheet_upload_blood_availability)
            bindCustomView(this)
            cornerRadius(20f)
            positiveButton(R.string.upload) {
                if (isInputVerified(this)) {
                    uploadSingleBloodAvailability(
                        bloodTypeSpinner.selectedItem as String,
                        billingTypeSpinner.selectedItem as String
                    )
                    dismiss()
                }
            }
            negativeButton(R.string.cancel) { dismiss() }
            noAutoDismiss()
        }
    }

    private fun uploadSingleBloodAvailability(bloodType: String, billingType: String) {
        viewModel.uploadBloodAvailability(UploadBloodAvailabilityRequest(bloodType, billingType,
            user.localID!!, user.phoneNumber!!, user.fullName(), user.religion ?: "Prefer Not To Say",
            user.location!!, user.userType!!))
    }

    private fun isInputVerified(bottomSheet: MaterialDialog): Boolean {
        bottomSheet.run {
            bloodErrorTextView.visibility = View.GONE
            billingErrorTextView.visibility = View.GONE
            return when {
                bloodTypeSpinner.selectedItemPosition == 0 -> {
                    bloodErrorTextView.visibility = View.VISIBLE
                    false
                }
                billingTypeSpinner.selectedItemPosition == 0 -> {
                    billingErrorTextView.visibility = View.VISIBLE
                    false
                }
                else -> true
            }
        }
    }

    private fun bindCustomView(bottomSheet: MaterialDialog) {
        bottomSheet.run {
            // Blood Type
            bloodTypeSpinner.adapter = SpinnerAdapter(context, Data.bloodTypes)
            bloodTypeSpinner.registerTextViewLabel(bloodTypeLabelTextView)

            // Billing Type
            billingTypeSpinner.adapter = SpinnerAdapter(context, Data.billingType)
            billingTypeSpinner.registerTextViewLabel(billingTypeLabelTextView)

            // Pre-filling Stuff
            user = prefsUtils.getPrefAsObject(PrefKeys.LOGGED_IN_USER_DATA, UserDetails::class.java)
            if (user.isBloodDonor()) {
                bloodTypeSpinner.setSelection(Data.bloodTypes.indexOf(user.bloodType))
                bloodTypeSpinner.isEnabled = false
            }
        }
    }

    private fun observe() {
        viewModel.hideShimmer.observe(this, Observer {
            if (it == true) {
                binding.shimmerLayout.hide()
                binding.textView2.visibility = View.VISIBLE
                viewModel.hideShimmerDone()
            }
        })
        viewModel.hideDeletingProgress.observe(this, Observer {
            if (it == true) {
                (binding.postingsRecyclerView.findViewHolderForLayoutPosition(currentPosition) as
                        BloodAvailabilityAdapter.ViewHolder).triggerDeletingProgress(false)
                viewModel.deletingProgressHidden()
            }
        })
        viewModel.fabVisibility.observe(this, Observer {
            if (it == true) binding.addAvailabilityFab.show() else if (it == false) binding.addAvailabilityFab.hide()
        })
        viewModel.loading.observe(this, Observer {
            if (it) binding.addAvailabilityFab.show() else binding.addAvailabilityFab.hide()
        })
    }

    private fun setUpToolbar() = mainActivity.run {
        setUpToolBar(getString(R.string.blood_availability))
        invalidateToolbarElevation(0)
    }

    override fun getViewModel(): BaseViewModel = viewModel
}
