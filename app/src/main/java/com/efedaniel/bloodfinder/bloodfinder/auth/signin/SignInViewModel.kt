package com.efedaniel.bloodfinder.bloodfinder.auth.signin

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.efedaniel.bloodfinder.R
import com.efedaniel.bloodfinder.base.BaseViewModel
import com.efedaniel.bloodfinder.bloodfinder.models.request.UserDetails
import com.efedaniel.bloodfinder.bloodfinder.repositories.AuthRepository
import com.efedaniel.bloodfinder.bloodfinder.repositories.DatabaseRepository
import com.efedaniel.bloodfinder.networkutils.GENERIC_ERROR_CODE
import com.efedaniel.bloodfinder.networkutils.GENERIC_ERROR_MESSAGE
import com.efedaniel.bloodfinder.networkutils.LoadingStatus
import com.efedaniel.bloodfinder.networkutils.Result
import com.efedaniel.bloodfinder.utils.*
import com.google.gson.Gson
import kotlinx.coroutines.launch
import javax.inject.Inject

class SignInViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val databaseRepository: DatabaseRepository,
    private val resourceProvider: ResourceProvider,
    private val prefsUtils: PrefsUtils
) : BaseViewModel() {

    enum class UserDetailsFlow {
        PROFILE,
        LOCATION,
        DASHBOARD
    }

    private val _signInSuccessful = MutableLiveData<UserDetailsFlow>()

    val signInSuccessful get() = _signInSuccessful

    fun signInUser(email: String, password: String) {
        prefsUtils.putString(PrefKeys.PREVIOUSLY_USED_EMAIL_ADDRESS, email)
        viewModelScope.launch {
            _loadingStatus.value = LoadingStatus.Loading(resourceProvider.getString(R.string.verifying_credentials))
            when (val result = authRepository.signInUser(email, password)) {
                is Result.Success -> {
                    prefsUtils.putObject(PrefKeys.LOGGED_IN_FIREBASE_AUTH_USER, result.data)
                    getUserDetails(result.data.localId)
                }
                is Result.Error -> {
                    if (result.errorCode.toInt() == APIDataKeys.INPUT_ERROR_CODE) {
                        _loadingStatus.value = LoadingStatus.Error(result.errorCode, when (result.errorMessage) {
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

    private fun getUserDetails(userID: String) {
        viewModelScope.launch {
            _loadingStatus.value = LoadingStatus.Loading(resourceProvider.getString(R.string.retrieving_account_details))
            val response = databaseRepository.getUserDetails(userID)
            if (response?.isSuccessful == true) {
                val userDetails: UserDetails? = Gson().fromJson(response.body(), UserDetails::class.java)
                if (userDetails != null) prefsUtils.putObject(PrefKeys.LOGGED_IN_USER_DATA, userDetails)
                _signInSuccessful.value = when {
                    userDetails == null -> UserDetailsFlow.PROFILE
                    userDetails.location == null -> UserDetailsFlow.LOCATION
                    else -> UserDetailsFlow.DASHBOARD
                }
                _loadingStatus.value = LoadingStatus.Success
            } else {
                _loadingStatus.value = LoadingStatus.Error(GENERIC_ERROR_CODE, GENERIC_ERROR_MESSAGE)
            }
        }
    }

    fun signInSuccessfulCompleted() {
        _signInSuccessful.value = null
    }

    override fun addAllLiveDataToObservablesList() {
        observablesList.add(signInSuccessful)
    }
}
