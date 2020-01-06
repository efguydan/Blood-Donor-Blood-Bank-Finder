package com.efedaniel.bloodfinder.bloodfinder.home.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.efedaniel.bloodfinder.R
import com.efedaniel.bloodfinder.base.BaseViewModel
import com.efedaniel.bloodfinder.bloodfinder.models.request.UserDetails
import com.efedaniel.bloodfinder.bloodfinder.models.response.UserSignUpResponse
import com.efedaniel.bloodfinder.bloodfinder.repositories.DatabaseRepository
import com.efedaniel.bloodfinder.networkutils.LoadingStatus
import com.efedaniel.bloodfinder.networkutils.Result
import com.efedaniel.bloodfinder.utils.PrefKeys
import com.efedaniel.bloodfinder.utils.PrefsUtils
import com.efedaniel.bloodfinder.utils.ResourceProvider
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val prefsUtils: PrefsUtils,
    private val resourceProvider: ResourceProvider,
    private val databaseRepository: DatabaseRepository
) : BaseViewModel() {

    private val _profileSavedAction = MutableLiveData(false)

    val profileSavedAction get() = _profileSavedAction

    fun saveUserDetails(userDetails: UserDetails) {
        val loggedInAuthUser = prefsUtils.getPrefAsObject(PrefKeys.LOGGED_IN_FIREBASE_AUTH_USER, UserSignUpResponse::class.java)
        userDetails.localID = loggedInAuthUser.localId
        viewModelScope.launch {
            _loadingStatus.value = LoadingStatus.Loading(resourceProvider.getString(R.string.saving_user_details))
            when (val result = databaseRepository.saveUserDetails(loggedInAuthUser.localId, userDetails)) {
                is Result.Success -> {
                    prefsUtils.putObject(PrefKeys.LOGGED_IN_USER_DATA, result.data)
                    _profileSavedAction.value = true
                    _loadingStatus.value = LoadingStatus.Success
                }
                is Result.Error -> {
                    _loadingStatus.value = LoadingStatus.Error(result.errorCode, result.errorMessage)
                }
            }
        }
    }

    fun profileSavedActionCompleted() {
        _profileSavedAction.value = false
    }

    override fun addAllLiveDataToObservablesList() {
        observablesList.add(profileSavedAction)
    }
}
