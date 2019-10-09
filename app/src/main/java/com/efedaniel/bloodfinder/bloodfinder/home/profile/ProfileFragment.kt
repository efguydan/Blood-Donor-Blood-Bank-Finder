package com.efedaniel.bloodfinder.bloodfinder.home.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.efedaniel.bloodfinder.App
import com.efedaniel.bloodfinder.R
import com.efedaniel.bloodfinder.base.BaseFragment
import com.efedaniel.bloodfinder.base.BaseViewModel
import com.efedaniel.bloodfinder.bloodfinder.reusables.SpinnerAdapter
import com.efedaniel.bloodfinder.databinding.FragmentProfileBinding
import com.efedaniel.bloodfinder.extensions.registerTextViewLabel
import com.efedaniel.bloodfinder.utils.Data
import javax.inject.Inject

class ProfileFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: FragmentProfileBinding
    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
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
        binding.userTypeSpinner.adapter = SpinnerAdapter(context!!, Data.userTypes)
        binding.userTypeSpinner.registerTextViewLabel(binding.userTypeLabelTextView)
        binding.proceedButton.setOnClickListener {
            findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToDashboardFragment())
        }
    }

    private fun setUpToolbar() = mainActivity.run {
        setUpToolBar(getString(R.string.profile), false)
        invalidateToolbarElevation(0)
    }

    override fun getViewModel(): BaseViewModel = viewModel
}
