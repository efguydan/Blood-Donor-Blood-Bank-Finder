package com.efedaniel.bloodfinder.bloodfinder.auth.signin

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.efedaniel.bloodfinder.R
import com.efedaniel.bloodfinder.base.BaseViewModel
import com.efedaniel.bloodfinder.bloodfinder.repositories.AuthRepository
import com.efedaniel.bloodfinder.networkutils.LoadingStatus
import com.efedaniel.bloodfinder.networkutils.Result
import com.efedaniel.bloodfinder.utils.APIDataKeys
import com.efedaniel.bloodfinder.utils.ErrorCodes
import com.efedaniel.bloodfinder.utils.ResourceProvider
import kotlinx.coroutines.launch
import javax.inject.Inject

class SignInViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val resourceProvider: ResourceProvider
): BaseViewModel() {

    private val _signInSuccessful = MutableLiveData(false)

    val signInSuccessful get() = _signInSuccessful

    fun signInUser(email: String, password: String) {
        viewModelScope.launch {
            _loadingStatus.value = LoadingStatus.Loading(resourceProvider.getString(R.string.signing_up))
            when (val result = authRepository.signInUser(email, password)) {
                is Result.Success -> {
                    _loadingStatus.value = LoadingStatus.Success
                    _signInSuccessful.value = true
                }
                is Result.Error -> {
                    if (result.errorCode.toInt() == APIDataKeys.INPUT_ERROR_CODE) {
                        _loadingStatus.value = LoadingStatus.Error(result.errorCode, when(result.errorMessage) {
                            ErrorCodes.EMAIL_NOT_FOUND -> resourceProvider.getString(R.string.account_hasnt_been_registered)
                            ErrorCodes.INVALID_EMAIL -> resourceProvider.getString(R.string.email_is_invalid)
                            ErrorCodes.INVALID_PASSWORD -> resourceProvider.getString(R.string.password_in_invalid)
                            ErrorCodes.USER_DISABLED -> resourceProvider.getString(R.string.account_has_been_disabled)
                            ErrorCodes.TOO_MANY_ATTEMPTS -> resourceProvider.getString(R.string.too_many_sign_in_attempts)
                            else -> result.errorMessage
                        })
                    } else {
                        _loadingStatus.value = LoadingStatus.Error(result.errorCode, result.errorMessage)
                    }
                }
            }
        }
    }

    fun signInSuccessfulCompleted() {
        _signInSuccessful.value = false
    }

    override fun addAllLiveDataToObservablesList() {
        observablesList.add(signInSuccessful)
    }

}