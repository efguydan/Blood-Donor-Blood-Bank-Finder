package com.efedaniel.bloodfinder.bloodfinder.auth.signup


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.efedaniel.bloodfinder.App

import com.efedaniel.bloodfinder.R
import com.efedaniel.bloodfinder.base.BaseFragment
import com.efedaniel.bloodfinder.base.BaseViewModel
import com.efedaniel.bloodfinder.databinding.FragmentSignUpBinding
import com.efedaniel.bloodfinder.extensions.onScrollChanged
import javax.inject.Inject

class SignUpFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: FragmentSignUpBinding
    private lateinit var viewModel: SignUpViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpToolbar()
        (mainActivity.applicationContext as App).component.inject(this)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SignUpViewModel::class.java)
        binding.viewModel = viewModel
        binding.layoutNestedScrollView.onScrollChanged {
            mainActivity.invalidateToolbarElevation(it)
        }
        binding.signUpButton.setOnClickListener {
            showDialogWithAction(
                title = "Success",
                body = "Your account has been successfully created. Please proceed to login",
                positiveRes = R.string.proceed,
                positiveAction = { findNavController().navigate(SignUpFragmentDirections.actionSignUpFragmentToSignInFragment()) })
        }
    }

    private fun setUpToolbar() = mainActivity.run {
        setUpToolBar(getString(R.string.sign_up), false)
        invalidateToolbarElevation(0)
    }

    override fun getViewModel(): BaseViewModel = viewModel
}
