package com.efedaniel.bloodfinder.bloodfinder.home.bloodavailabilty


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.bottomsheets.BottomSheet
import com.efedaniel.bloodfinder.App

import com.efedaniel.bloodfinder.R
import com.efedaniel.bloodfinder.base.BaseFragment
import com.efedaniel.bloodfinder.base.BaseViewModel
import com.efedaniel.bloodfinder.databinding.FragmentBloodAvailabiltyBinding
import javax.inject.Inject

class BloodAvailabilityFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: FragmentBloodAvailabiltyBinding
    private lateinit var viewModel: BloodAvailabilityViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
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
        binding.addAvailabilityFab.setOnClickListener { setupNewEntryDialog() }
    }

    private fun setupNewEntryDialog() {
        MaterialDialog(mainActivity, BottomSheet()).show {
            title(R.string.blood_availability)
            message(text="We want to upload a new blood availability")
            positiveButton(R.string.proceed) {
                showSnackbar(R.string.proceed)
            }
            negativeButton(R.string.cancel)
        }
    }

    private fun setUpToolbar() = mainActivity.run {
        setUpToolBar(getString(R.string.blood_availability))
        invalidateToolbarElevation(0)
    }

    override fun getViewModel(): BaseViewModel = viewModel
}
