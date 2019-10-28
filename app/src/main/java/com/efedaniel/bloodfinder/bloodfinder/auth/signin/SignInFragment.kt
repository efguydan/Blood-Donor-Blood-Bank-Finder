package com.efedaniel.bloodfinder.bloodfinder.auth.signin

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
import com.efedaniel.bloodfinder.databinding.FragmentSignInBinding
import javax.inject.Inject

class SignInFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: FragmentSignInBinding
    private lateinit var viewModel: SignInViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignInBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpToolbar()
        (mainActivity.applicationContext as App).component.inject(this)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SignInViewModel::class.java)
        binding.viewModel = viewModel
        binding.signUpTextView.setOnClickListener {
            findNavController().navigate(SignInFragmentDirections.actionSignInFragmentToSignUpFragment())
        }
        binding.signInButton.setOnClickListener {
            if (binding.idEditText.text.toString().trim().isEmpty()) {
                showSnackbar(R.string.email_cant_be_empty)
                return@setOnClickListener
            }
            if (binding.passwordEditText.text.toString().length < 6) {
                showSnackbar(R.string.password_must_be_at_least_chars)
                return@setOnClickListener
            }
            viewModel.signInUser(
                binding.idEditText.text.toString().trim(),
                binding.passwordEditText.text.toString()
            )
        }
        binding.forgotPasswordTextView.setOnClickListener {
            findNavController().navigate(SignInFragmentDirections.actionSignInFragmentToForgotPasswordFragment())
        }
        viewModel.signInSuccessful.observe(this, Observer {
            if (it == null) return@Observer
            findNavController().navigate(when(it) {
                SignInViewModel.UserDetailsFlow.PROFILE -> SignInFragmentDirections.actionSignInFragmentToProfileFragment()
                else -> SignInFragmentDirections.actionSignInFragmentToDashboardFragment()
            })
            viewModel.signInSuccessfulCompleted()
        })
    }

    private fun setUpToolbar() = mainActivity.run {
        setUpToolBar("", true)
        invalidateToolbarElevation(0)
    }

    override fun getViewModel(): BaseViewModel = viewModel
}
