package com.efedaniel.bloodfinder.bloodfinder.notifications.bloodPostingResponse

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.efedaniel.bloodfinder.App

import com.efedaniel.bloodfinder.R
import com.efedaniel.bloodfinder.base.BaseFragment
import com.efedaniel.bloodfinder.base.BaseViewModel
import com.efedaniel.bloodfinder.databinding.FragmentBloodPostingResponseBinding
import javax.inject.Inject

class BloodPostingResponseFragment : BaseFragment() {

    companion object {
        const val BLOOD_POSTING_RESPONSE_KEY = "blood_posting_response_key"
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: FragmentBloodPostingResponseBinding
    private lateinit var viewModel: BloodPostingResponseViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBloodPostingResponseBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpToolbar()
        (mainActivity.applicationContext as App).component.inject(this)
        binding.viewModel = viewModel
    }

    private fun setUpToolbar() = mainActivity.run {
        setUpToolBar(getString(R.string.blood_donation_response), false)
        invalidateToolbarElevation(100)
    }

    override fun getViewModel(): BaseViewModel = viewModel
}
