package com.efedaniel.bloodfinder.bloodfinder.home.viewprofile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.efedaniel.bloodfinder.App

import com.efedaniel.bloodfinder.R
import com.efedaniel.bloodfinder.base.BaseFragment
import com.efedaniel.bloodfinder.base.BaseViewModel
import com.efedaniel.bloodfinder.bloodfinder.models.request.UserDetails
import com.efedaniel.bloodfinder.databinding.FragmentViewProfileBinding
import com.efedaniel.bloodfinder.extensions.hide
import com.efedaniel.bloodfinder.utils.PrefKeys
import com.efedaniel.bloodfinder.utils.PrefsUtils
import javax.inject.Inject

class ViewProfileFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var prefsUtils: PrefsUtils

    private lateinit var binding: FragmentViewProfileBinding
    private lateinit var viewModel: ViewProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentViewProfileBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpToolbar()
        (mainActivity.applicationContext as App).component.inject(this)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ViewProfileViewModel::class.java)
        binding.viewModel = viewModel

        val userDetails = prefsUtils.getPrefAsObject(PrefKeys.LOGGED_IN_USER_DATA, UserDetails::class.java)
        binding.userDetails = userDetails
        binding.executePendingBindings()
        if (!userDetails.isBloodDonor()) {
            binding.apply {
                genderLabelTextView.hide()
                genderTextView.hide()
                bloodTypeLabelTextView.hide()
                bloodTypeTextView.hide()
                religionLabelTextView.hide()
                religionTextView.hide()
                maritalStatusLabelTextView.hide()
                maritalStatusTextView.hide()
            }
        }
    }

    private fun setUpToolbar() = mainActivity.run {
        setUpToolBar(getString(R.string.profile), true)
        invalidateToolbarElevation(0)
    }

    override fun getViewModel(): BaseViewModel = viewModel
}
