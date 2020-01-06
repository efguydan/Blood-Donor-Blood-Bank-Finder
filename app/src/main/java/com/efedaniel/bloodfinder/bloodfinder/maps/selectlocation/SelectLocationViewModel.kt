package com.efedaniel.bloodfinder.bloodfinder.maps.selectlocation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.efedaniel.bloodfinder.R
import com.efedaniel.bloodfinder.base.BaseViewModel
import com.efedaniel.bloodfinder.bloodfinder.models.MiniLocation
import com.efedaniel.bloodfinder.bloodfinder.models.request.UserDetails
import com.efedaniel.bloodfinder.bloodfinder.repositories.DatabaseRepository
import com.efedaniel.bloodfinder.networkutils.LoadingStatus
import com.efedaniel.bloodfinder.networkutils.Result
import com.efedaniel.bloodfinder.utils.PrefKeys
import com.efedaniel.bloodfinder.utils.PrefsUtils
import com.efedaniel.bloodfinder.utils.ResourceProvider
import kotlinx.coroutines.launch
import javax.inject.Inject

class SelectLocationViewModel @Inject constructor(
    private val resourceProvider: ResourceProvider,
    private val databaseRepository: DatabaseRepository,
    private val prefsUtils: PrefsUtils
) : BaseViewModel() {

    private val _locationSavedAction = MutableLiveData(false)
    val locationSavedAction: LiveData<Boolean> get() = _locationSavedAction

    fun saveUserLocation(latitude: String, longitude: String) {
        val user = prefsUtils.getPrefAsObject(PrefKeys.LOGGED_IN_USER_DATA, UserDetails::class.java)
        viewModelScope.launch {
            _loadingStatus.value = LoadingStatus.Loading(resourceProvider.getString(R.string.saving_user_location))
            when (val result = databaseRepository.saveUserLocation(user.localID!!, MiniLocation(latitude, longitude, user.address))) {
                is Result.Success -> {
                    user.location = result.data
                    prefsUtils.putObject(PrefKeys.LOGGED_IN_USER_DATA, user)
                    _locationSavedAction.value = true
                    _loadingStatus.value = LoadingStatus.Success
                }
                is Result.Error -> {
                    _loadingStatus.value = LoadingStatus.Error(result.errorCode, result.errorMessage)
                }
            }
        }
    }

    fun locationSavedActionCompleted() {
        _locationSavedAction.value = false
    }

    override fun addAllLiveDataToObservablesList() {
        observablesList.add(locationSavedAction)
    }
}
