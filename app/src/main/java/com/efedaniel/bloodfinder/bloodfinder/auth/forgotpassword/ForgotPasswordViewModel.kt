package com.efedaniel.bloodfinder.bloodfinder.auth.forgotpassword

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

class ForgotPasswordViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val resourceProvider: ResourceProvider
) : BaseViewModel() {

    private val _resetSentToMail = MutableLiveData(false)

    val resetSentToMail get() = _resetSentToMail

    fun resetUserPassword(email: String) {
        viewModelScope.launch {
            _loadingStatus.value = LoadingStatus.Loading(resourceProvider.getString(R.string.please_wait))
            when (val result = authRepository.resetUserPassword(email)) {
                is Result.Success -> {
                    _resetSentToMail.value = true
                    _loadingStatus.value = LoadingStatus.Success
                }
                is Result.Error -> {
                    if (result.errorCode.toInt() == APIDataKeys.INPUT_ERROR_CODE) {
                        _loadingStatus.value = LoadingStatus.Error(result.errorCode, when (result.errorMessage) {
                            ErrorCodes.EMAIL_NOT_FOUND -> resourceProvider.getString(R.string.email_does_not_exist_in_db)
                            ErrorCodes.MISSING_EMAIL -> resourceProvider.getString(R.string.email_cant_be_empty)
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

    fun resetSentToMailCompleted() {
        _resetSentToMail.value = false
    }

    override fun addAllLiveDataToObservablesList() {
        observablesList.add(resetSentToMail)
    }
}
