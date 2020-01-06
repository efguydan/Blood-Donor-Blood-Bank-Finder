package com.efedaniel.bloodfinder.bloodfinder.auth.signup

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
import com.efedaniel.bloodfinder.databinding.FragmentSignUpBinding
import com.efedaniel.bloodfinder.extensions.onScrollChanged
import javax.inject.Inject

class SignUpFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: FragmentSignUpBinding
    private lateinit var viewModel: SignUpViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
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
            if (binding.emailEditText.text.toString().trim().isEmpty()) {
                showSnackbar(R.string.email_cant_be_empty)
                return@setOnClickListener
            }
            if (binding.passwordEditText.text.toString() != binding.confirmPasswordEditText.text.toString()) {
                showSnackbar(R.string.passwords_dont_match)
                return@setOnClickListener
            }
            if (binding.passwordEditText.text.toString().length < 6) {
                showSnackbar(R.string.password_must_be_at_least_chars)
                return@setOnClickListener
            }
            viewModel.signUpUser(
                binding.emailEditText.text.toString(),
                binding.passwordEditText.text.toString()
            )
        }
        viewModel.signUpSuccessful.observe(this, Observer {
            if (it == false) return@Observer
            showDialogWithAction(
                title = getString(R.string.success),
                body = getString(R.string.account_successfully_created),
                positiveRes = R.string.proceed,
                positiveAction = {
                    findNavController().navigate(SignUpFragmentDirections.actionSignUpFragmentToSignInFragment())
                }
            )
            viewModel.signUpSuccessfulCompleted()
        })
    }

    private fun setUpToolbar() = mainActivity.run {
        setUpToolBar(getString(R.string.sign_up))
        invalidateToolbarElevation(0)
    }

    override fun getViewModel(): BaseViewModel = viewModel
}
