package com.efedaniel.bloodfinder.bloodfinder.auth.signup

import androidx.lifecycle.viewModelScope
import com.efedaniel.bloodfinder.R
import com.efedaniel.bloodfinder.base.BaseViewModel
import com.efedaniel.bloodfinder.bloodfinder.repositories.AuthRepository
import com.efedaniel.bloodfinder.networkutils.LoadingStatus
import com.efedaniel.bloodfinder.utils.PrefsUtils
import com.efedaniel.bloodfinder.utils.ResourceProvider
import com.efedaniel.bloodfinder.networkutils.Result
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class SignUpViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val resourceProvider: ResourceProvider
): BaseViewModel() {

    fun signUpUser(email: String, password: String) {
        viewModelScope.launch {
            _loadingStatus.value = LoadingStatus.Loading(resourceProvider.getString(R.string.signing_up))
            when (val result = authRepository.signUpUser(email, password)) {
                is Result.Success -> {
                    Timber.d(result.data.localId)
                    _loadingStatus.value = LoadingStatus.Success
                    _snackMessage.value = "Successfully Signed Up"
                }
                is Result.Error -> _loadingStatus.value = LoadingStatus.Error(result.errorCode, result.errorMessage)
            }
        }
    }

    override fun addAllLiveDataToObservablesList() {

    }

}