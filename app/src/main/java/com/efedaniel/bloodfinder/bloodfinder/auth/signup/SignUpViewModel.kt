package com.efedaniel.bloodfinder.bloodfinder.auth.signup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.efedaniel.bloodfinder.R
import com.efedaniel.bloodfinder.base.BaseViewModel
import com.efedaniel.bloodfinder.bloodfinder.repositories.AuthRepository
import com.efedaniel.bloodfinder.networkutils.LoadingStatus
import com.efedaniel.bloodfinder.utils.ResourceProvider
import com.efedaniel.bloodfinder.networkutils.Result
import com.efedaniel.bloodfinder.utils.APIDataKeys
import com.efedaniel.bloodfinder.utils.ErrorCodes
import kotlinx.coroutines.launch
import javax.inject.Inject

class SignUpViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val resourceProvider: ResourceProvider
) : BaseViewModel() {

    private val _signUpSuccessful = MutableLiveData(false)

    val signUpSuccessful get() = _signUpSuccessful

    fun signUpUser(email: String, password: String) {
        viewModelScope.launch {
            _loadingStatus.value = LoadingStatus.Loading(resourceProvider.getString(R.string.signing_up))
            when (val result = authRepository.signUpUser(email, password)) {
                is Result.Success -> {
                    _signUpSuccessful.value = true
                    _loadingStatus.value = LoadingStatus.Success
                }
                is Result.Error -> {
                    if (result.errorCode.toInt() == APIDataKeys.INPUT_ERROR_CODE) {
                        _loadingStatus.value = LoadingStatus.Error(result.errorCode, when (result.errorMessage) {
                            ErrorCodes.EMAIL_EXISTS -> resourceProvider.getString(R.string.email_exists)
                            ErrorCodes.INVALID_EMAIL -> resourceProvider.getString(R.string.email_is_invalid)
                            else -> result.errorMessage
                        })
                    } else {
                        _loadingStatus.value = LoadingStatus.Error(result.errorCode, result.errorMessage)
                    }
                }
            }
        }
    }

    fun signUpSuccessfulCompleted() {
        _signUpSuccessful.value = false
    }

    override fun addAllLiveDataToObservablesList() {
        observablesList.add(signUpSuccessful)
    }
}
