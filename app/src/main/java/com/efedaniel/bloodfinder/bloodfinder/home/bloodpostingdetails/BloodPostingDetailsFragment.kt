package com.efedaniel.bloodfinder.bloodfinder.home.bloodpostingdetails

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.efedaniel.bloodfinder.App

import com.efedaniel.bloodfinder.R
import com.efedaniel.bloodfinder.base.BaseFragment
import com.efedaniel.bloodfinder.base.BaseViewModel
import com.efedaniel.bloodfinder.bloodfinder.models.request.BloodPostingRequest
import com.efedaniel.bloodfinder.bloodfinder.models.request.UploadBloodAvailabilityRequest
import com.efedaniel.bloodfinder.bloodfinder.models.request.UserDetails
import com.efedaniel.bloodfinder.databinding.FragmentBloodPostingDetailsBinding
import com.efedaniel.bloodfinder.utils.Misc
import com.efedaniel.bloodfinder.utils.PrefKeys
import com.efedaniel.bloodfinder.utils.PrefsUtils
import javax.inject.Inject

class BloodPostingDetailsFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject
    lateinit var prefsUtils: PrefsUtils

    private lateinit var binding: FragmentBloodPostingDetailsBinding
    private lateinit var viewModel: BloodPostingDetailsViewModel
    private lateinit var bloodPosting: UploadBloodAvailabilityRequest

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBloodPostingDetailsBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpToolbar()
        (mainActivity.applicationContext as App).component.inject(this)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(BloodPostingDetailsViewModel::class.java)
        binding.viewModel = viewModel
        bloodPosting = BloodPostingDetailsFragmentArgs.fromBundle(arguments!!).bloodPosting

        viewModel.getPostingUserDetails(bloodPosting.donorID)
        viewModel.bloodPostingUserDetails.observe(this, Observer {
            if (it != null) { bind(it) }
        })
    }

    private fun bind(userDetails: UserDetails) {
        binding.bloodTypeTextView.text = bloodPosting.bloodType
        binding.userNameTextView.text = userDetails.fullName()
        binding.userTypeTextView.text = userDetails.userType
        binding.addressTextView.text = userDetails.address
        binding.bilingTypeTextView.text = String.format("%s Donation", bloodPosting.billingType)
        binding.phoneNumberTextView.text = userDetails.phoneNumber
        binding.phoneNumberTextView.setOnClickListener { call(userDetails.phoneNumber!!) }

        binding.selectThisDonorButton.setOnClickListener {
            val currentUser = prefsUtils.getPrefAsObject(PrefKeys.LOGGED_IN_USER_DATA, UserDetails::class.java)
            viewModel.uploadBloodPostingRequest(
                BloodPostingRequest(
                    bloodPosting.bloodAvailabilityID!!,
                    bloodPosting.donorID,
                    currentUser.localID!!,
                    bloodPosting.donorName,
                    currentUser.fullName(),
                    bloodPosting.bloodType,
                    bloodPosting.billingType,
                    currentUser.userType!!,
                    bloodPosting.donorType
                ))
        }
        viewModel.notificationSentSuccessfully.observe(this, Observer {
            if (it == true) {
                showDialogWithAction(
                    title = getString(R.string.blood_requested_successfully),
                    body = "Your blood request has been sent to ${bloodPosting.donorName}, Please wait for a response from them.",
                    positiveRes = R.string.close,
                    positiveAction = {
                        findNavController().navigate(BloodPostingDetailsFragmentDirections.actionBloodPostingDetailsFragmentToDashboardFragment())
                    }
                )
            }
        })
    }

    private fun call(phoneNumber: String) {
        val phoneIntent = Intent(Intent.ACTION_CALL)
        phoneIntent.data = Uri.parse("tel:$phoneNumber")
        if (ActivityCompat.checkSelfPermission(context!!, android.Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            startActivity(phoneIntent)
        } else {
            requestPermissions(arrayOf(android.Manifest.permission.CALL_PHONE), Misc.CALL_PERMISSION_REQUEST)
        }
    }

    override fun getViewModel(): BaseViewModel = viewModel

    private fun setUpToolbar() = mainActivity.run {
        setUpToolBar(getString(R.string.blood_posting_details), true)
        invalidateToolbarElevation(100)
    }
}
