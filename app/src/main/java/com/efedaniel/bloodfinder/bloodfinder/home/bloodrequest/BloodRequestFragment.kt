package com.efedaniel.bloodfinder.bloodfinder.home.bloodrequest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.efedaniel.bloodfinder.App
import com.efedaniel.bloodfinder.R
import com.efedaniel.bloodfinder.base.BaseFragment
import com.efedaniel.bloodfinder.base.BaseViewModel
import com.efedaniel.bloodfinder.bloodfinder.reusables.SpinnerAdapter
import com.efedaniel.bloodfinder.databinding.FragmentBloodRequestBinding
import com.efedaniel.bloodfinder.extensions.registerTextViewLabel
import com.efedaniel.bloodfinder.utils.Data
import javax.inject.Inject

class BloodRequestFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: FragmentBloodRequestBinding
    private lateinit var viewModel: BloodRequestViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBloodRequestBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpToolbar()
        (mainActivity.applicationContext as App).component.inject(this)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(BloodRequestViewModel::class.java)
        binding.viewModel = viewModel
        setupSpinners()
        binding.searchButton.setOnClickListener {
            if (isInputVerified()) {
                viewModel.getCompatibleBloods(
                    binding.bloodTypeSpinner.selectedItem as String,
                    binding.billingTypeSpinner.selectedItem as String,
                    binding.kindOfDonorSpinner.selectedItem as String
                )
            }
        }
        viewModel.moveToBloodResults.observe(this, Observer {
            if (it == true) {
                findNavController().navigate(BloodRequestFragmentDirections.actionBloodRequestFragmentToBloodResultsFragment(viewModel.donorList.toTypedArray()))
                viewModel.moveToBloodResultsDone()
            }
        })
    }

    private fun isInputVerified(): Boolean {
        if (binding.bloodTypeSpinner.selectedItemPosition == 0) {
            showSnackbar(R.string.please_select_blood_type)
            return false
        }
        if (binding.billingTypeSpinner.selectedItemPosition == 0) {
            showSnackbar(R.string.please_select_billing_type)
            return false
        }
        if (binding.kindOfDonorSpinner.selectedItemPosition == 0) {
            showSnackbar(R.string.please_select_kind_of_donor)
            return false
        }
        return true
    }

    private fun setupSpinners() {
        // Blood Type
        binding.bloodTypeSpinner.adapter = SpinnerAdapter(context!!, Data.bloodTypes)
        binding.bloodTypeSpinner.registerTextViewLabel(binding.bloodTypeLabelTextView)

        // Billing Type
        binding.billingTypeSpinner.registerTextViewLabel(binding.billingTypeLabelTextView)
        binding.billingTypeSpinner.adapter = SpinnerAdapter(context!!, Data.billingTypeWithAny)

        // Kind of Donor
        binding.kindOfDonorSpinner.registerTextViewLabel(binding.kindOfDonorLabelTextView)
        binding.kindOfDonorSpinner.adapter = SpinnerAdapter(context!!, Data.kindOfBloodDonor)
    }

    private fun setUpToolbar() = mainActivity.run {
        setUpToolBar(getString(R.string.blood_request), true)
        invalidateToolbarElevation(0)
    }

    override fun getViewModel(): BaseViewModel = viewModel
}
