package com.efedaniel.bloodfinder.bloodfinder.auth.forgotpassword

import androidx.lifecycle.viewModelScope
import com.efedaniel.bloodfinder.R
import com.efedaniel.bloodfinder.base.BaseViewModel
import com.efedaniel.bloodfinder.bloodfinder.repositories.AuthRepository
import com.efedaniel.bloodfinder.networkutils.LoadingStatus
import com.efedaniel.bloodfinder.networkutils.Result
import com.efedaniel.bloodfinder.utils.ResourceProvider
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class ForgotPasswordViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val resourceProvider: ResourceProvider
): BaseViewModel() {

    fun resetUserPassword(email: String) {
        viewModelScope.launch {
            _loadingStatus.value = LoadingStatus.Loading(resourceProvider.getString(R.string.signing_up))
            when (val result = authRepository.resetUserPassword(email)) {
                is Result.Success -> {
                    _loadingStatus.value = LoadingStatus.Success
                    _snackMessage.value = "Reset Mail Sent to Mail"
                }
                is Result.Error -> _loadingStatus.value = LoadingStatus.Error(result.errorCode, result.errorMessage)
            }
        }
    }

    override fun addAllLiveDataToObservablesList() {

    }

}