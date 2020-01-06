package com.efedaniel.bloodfinder.bloodfinder.home.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.efedaniel.bloodfinder.App
import com.efedaniel.bloodfinder.R
import com.efedaniel.bloodfinder.base.BaseFragment
import com.efedaniel.bloodfinder.base.BaseViewModel
import com.efedaniel.bloodfinder.bloodfinder.models.request.UserDetails
import com.efedaniel.bloodfinder.bloodfinder.reusables.SpinnerAdapter
import com.efedaniel.bloodfinder.databinding.FragmentProfileBinding
import com.efedaniel.bloodfinder.extensions.hide
import com.efedaniel.bloodfinder.extensions.onScrollChanged
import com.efedaniel.bloodfinder.extensions.registerTextViewLabel
import com.efedaniel.bloodfinder.extensions.show
import com.efedaniel.bloodfinder.utils.Data
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.fragment_profile.*
import javax.inject.Inject

class ProfileFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: FragmentProfileBinding
    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpToolbar()
        (mainActivity.applicationContext as App).component.inject(this)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ProfileViewModel::class.java)
        binding.viewModel = viewModel
        setupSpinners()
        binding.layoutNestedScrollView.onScrollChanged {
            mainActivity.invalidateToolbarElevation(it)
        }
        binding.proceedButton.setOnClickListener {
            if (inputVerified()) saveUserDetails()
        }
        viewModel.profileSavedAction.observe(this, Observer {
            if (it == true) {
                findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToSelectLocationFragment())
                viewModel.profileSavedActionCompleted()
            }
        })
    }

    private fun saveUserDetails() {
        val userDetails = UserDetails()
        userDetails.userType = binding.userTypeSpinner.selectedItem as String
        when (userDetails.userType) {
            "Blood Donor" -> {
                userDetails.firstName = binding.firstNameEditText.text.toString().trim()
                userDetails.gender = binding.genderSpinner.selectedItem as String
                userDetails.lastName = binding.lastNameEditText.text.toString().trim()
                userDetails.maritalStatus = binding.maritalStatusSpinner.selectedItem as String
                userDetails.religion = binding.religionSpinner.selectedItem as String
                userDetails.title = binding.titleSpinner.selectedItem as String
                userDetails.bloodType = binding.bloodTypeSpinner.selectedItem as String
            }
            else -> {
                userDetails.institutionName = binding.firstNameEditText.text.toString().trim()
            }
        }
        userDetails.address = binding.addressEditText.text.toString().trim()
        userDetails.phoneNumber = binding.phoneNumberEditText.text.toString().trim()
        viewModel.saveUserDetails(userDetails)
    }

    private fun setupSpinners() {
        // UserType
        binding.userTypeSpinner.adapter = SpinnerAdapter(context!!, Data.userTypes)
        binding.userTypeSpinner.registerTextViewLabel(binding.userTypeLabelTextView)
        binding.userTypeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) { }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (position > 0) {
                    binding.userTypeLabelTextView.show()
                    binding.firstNameLayout.show()
                }
                when (position) {
                    1 -> {
                        binding.bloodDonorGroup.show()
                        binding.firstNameLayout.hint = getString(R.string.first_name)
                    }
                    2 -> {
                        binding.bloodDonorGroup.hide()
                        binding.firstNameLayout.hint = getString(R.string.name_of_hospital)
                    }
                    3 -> {
                        binding.bloodDonorGroup.hide()
                        binding.firstNameLayout.hint = getString(R.string.name_of_blood_bank)
                    }
                }
            }
        }

        // Title
        binding.titleSpinner.adapter = SpinnerAdapter(context!!, Data.titleTypes)
        binding.titleSpinner.registerTextViewLabel(binding.titleLabelTextView)

        // Gender
        binding.genderSpinner.adapter = SpinnerAdapter(context!!, Data.genderTypes)
        binding.genderSpinner.registerTextViewLabel(binding.genderLabelTextView)

        // Blood Type
        binding.bloodTypeSpinner.adapter = SpinnerAdapter(context!!, Data.bloodTypes)
        binding.bloodTypeSpinner.registerTextViewLabel(binding.bloodTypeLabelTextView)

        // Religion
        binding.religionSpinner.adapter = SpinnerAdapter(context!!, Data.religionTypes)
        binding.religionSpinner.registerTextViewLabel(binding.religionLabelTextView)

        // Marital Status
        binding.maritalStatusSpinner.adapter = SpinnerAdapter(context!!, Data.maritalStatusTypes)
        binding.maritalStatusSpinner.registerTextViewLabel(binding.maritalStatusTextView)
    }

    private fun inputVerified(): Boolean {
        var verified = true
        when (binding.userTypeSpinner.selectedItemPosition) {
            0 -> {
                showSnackbar(R.string.select_user_type)
                return false
            }
            1 -> {
                if (binding.titleSpinner.selectedItemPosition == 0) {
                    showSnackbar(getString(R.string.please_select_title))
                    return false
                }
                if (!verifyEditTexts(listOf(lastNameEditText))) {
                    verified = false
                }
                if (binding.genderSpinner.selectedItemPosition == 0) {
                    showSnackbar(getString(R.string.please_select_gender))
                    return false
                }
                if (binding.bloodTypeSpinner.selectedItemPosition == 0) {
                    showSnackbar(getString(R.string.please_select_blood_type))
                    return false
                }
                if (binding.maritalStatusSpinner.selectedItemPosition == 0) {
                    showSnackbar(getString(R.string.please_select_marital_status))
                    return false
                }
            }
        }
        if (phoneNumberEditText.text.toString().length < 11) {
            phoneNumberEditText.error = getString(R.string.incomplete_phone_number)
            verified = false
        }
        if (!verifyEditTexts(listOf(firstNameEditText, addressEditText))) {
            verified = false
        }
        return verified
    }

    private fun verifyEditTexts(editTexts: List<TextInputEditText>): Boolean {
        var verified = true
        for (editText in editTexts) {
            if (editText.text.toString().trim().isNullOrEmpty()) {
                editText.error = getString(R.string.field_cant_be_empty)
                verified = false
            }
        }
        return verified
    }

    private fun setUpToolbar() = mainActivity.run {
        setUpToolBar(getString(R.string.profile), false)
        invalidateToolbarElevation(0)
    }

    override fun getViewModel(): BaseViewModel = viewModel
}
