package com.efedaniel.bloodfinder.bloodfinder.auth.forgotpassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.efedaniel.bloodfinder.App

import com.efedaniel.bloodfinder.R
import com.efedaniel.bloodfinder.base.BaseFragment
import com.efedaniel.bloodfinder.base.BaseViewModel
import com.efedaniel.bloodfinder.databinding.FragmentForgotPasswordBinding
import javax.inject.Inject

class ForgotPasswordFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: FragmentForgotPasswordBinding
    private lateinit var viewModel: ForgotPasswordViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentForgotPasswordBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpToolbar()
        (mainActivity.applicationContext as App).component.inject(this)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ForgotPasswordViewModel::class.java)
        binding.viewModel = viewModel
        binding.proceedButton.setOnClickListener {
            if (binding.emailEditText.text.toString().trim().isEmpty()) {
                showSnackbar(R.string.email_cant_be_empty)
            } else {
                viewModel.resetUserPassword(binding.emailEditText.text.toString().trim())
            }
        }
        viewModel.resetSentToMail.observe(this, Observer {
            if (it == false) return@Observer
            showDialogWithAction(
                title = getString(R.string.success),
                body = String.format(getString(R.string.reset_sent_to_mail), binding.emailEditText.text.toString()),
                positiveAction = {
                    mainActivity.onBackPressed()
                }
            )
            viewModel.resetSentToMailCompleted()
        })
    }

    private fun setUpToolbar() = mainActivity.run {
        setUpToolBar(getString(R.string.reset_password))
        invalidateToolbarElevation(0)
    }

    override fun getViewModel(): BaseViewModel = viewModel
}
