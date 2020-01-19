package com.efedaniel.bloodfinder.bloodfinder.notifications.bloodPostingResponse

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
import com.efedaniel.bloodfinder.App

import com.efedaniel.bloodfinder.R
import com.efedaniel.bloodfinder.base.BaseFragment
import com.efedaniel.bloodfinder.base.BaseViewModel
import com.efedaniel.bloodfinder.bloodfinder.models.request.BloodPostingRequest
import com.efedaniel.bloodfinder.bloodfinder.models.request.UserDetails
import com.efedaniel.bloodfinder.databinding.FragmentBloodPostingResponseBinding
import com.efedaniel.bloodfinder.extensions.hide
import com.efedaniel.bloodfinder.utils.ApiKeys
import com.efedaniel.bloodfinder.utils.Misc
import javax.inject.Inject

class BloodPostingResponseFragment : BaseFragment() {

    companion object {
        const val BLOOD_POSTING_RESPONSE_KEY = "blood_posting_response_key"
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: FragmentBloodPostingResponseBinding
    private lateinit var viewModel: BloodPostingResponseViewModel
    private lateinit var bloodPosting: BloodPostingRequest

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBloodPostingResponseBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpToolbar(arguments!!.getParcelable<BloodPostingRequest>(BLOOD_POSTING_RESPONSE_KEY) == null)
        (mainActivity.applicationContext as App).component.inject(this)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(BloodPostingResponseViewModel::class.java)
        binding.viewModel = viewModel

        bloodPosting = arguments!!.getParcelable(BLOOD_POSTING_RESPONSE_KEY) ?: BloodPostingResponseFragmentArgs.fromBundle(arguments!!).bloodPosting

        viewModel.getBloodProviderData(bloodPosting.bloodProviderID)
        viewModel.bloodProviderUserData.observe(this, Observer { if (it != null) { bind(it) } })
    }

    private fun bind(userDetails: UserDetails) {
        binding.bloodRequestMessageTextView.text = String.format(getString(R.string.blood_response_message_format),
            bloodPosting.bloodSeekerFullName, bloodPosting.status, bloodPosting.bloodProviderFullName,
            getString(when (bloodPosting.status) {
                ApiKeys.ACCEPTED -> R.string.blood_request_accepted_message
                else -> R.string.blood_request_declined_message
            }))
        binding.bloodProviderNameTextView.text = bloodPosting.bloodProviderFullName
        binding.bloodProviderAddressTextView.text = userDetails.address
        binding.phoneNumberTextView.text = userDetails.phoneNumber
        binding.phoneNumberTextView.setOnClickListener { call(userDetails.phoneNumber!!) }
        binding.billingTypeTextView.text = String.format("%s Donation", bloodPosting.billingType)
        if (bloodPosting.status == ApiKeys.ACCEPTED) {
            binding.viewRouteButton.setOnClickListener {
                launchMapsActivity()
            }
        } else {
            binding.viewRouteButton.hide()
        }
    }

    private fun launchMapsActivity() {
        val location = viewModel.bloodProviderUserData.value!!.location!!
        val mapsIntentUri = Uri.parse("google.navigation:q=${location.latitude},${location.longitude}")
        val mapsIntent = Intent(Intent.ACTION_VIEW, mapsIntentUri)
        mapsIntent.setPackage(Misc.MAPS_APP_ID)
        if (mapsIntent.resolveActivity(mainActivity.packageManager) != null) {
            startActivity(mapsIntent)
        } else {
            showDialogWithAction(
                body = getString(R.string.google_maps_not_installed),
                positiveRes = R.string.proceed,
                positiveAction = {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(Misc.MAPS_PLAY_STORE_URL)))
                },
                negativeRes = R.string.close
            )
        }
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

    private fun setUpToolbar(showUpIcon: Boolean) = mainActivity.run {
        setUpToolBar(getString(R.string.donation_response), showUpIcon)
        invalidateToolbarElevation(0)
    }

    override fun getViewModel(): BaseViewModel = viewModel
}
