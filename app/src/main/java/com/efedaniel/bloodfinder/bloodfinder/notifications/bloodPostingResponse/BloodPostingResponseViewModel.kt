package com.efedaniel.bloodfinder.bloodfinder.notifications.bloodPostingResponse

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.efedaniel.bloodfinder.R
import com.efedaniel.bloodfinder.base.BaseViewModel
import com.efedaniel.bloodfinder.bloodfinder.models.request.UserDetails
import com.efedaniel.bloodfinder.bloodfinder.repositories.DatabaseRepository
import com.efedaniel.bloodfinder.networkutils.GENERIC_ERROR_CODE
import com.efedaniel.bloodfinder.networkutils.GENERIC_ERROR_MESSAGE
import com.efedaniel.bloodfinder.networkutils.LoadingStatus
import com.efedaniel.bloodfinder.utils.ResourceProvider
import com.google.gson.Gson
import kotlinx.coroutines.launch
import javax.inject.Inject

class BloodPostingResponseViewModel @Inject constructor(
    private val resourceProvider: ResourceProvider,
    private val databaseRepository: DatabaseRepository
) : BaseViewModel() {

    private val _bloodProviderUserData = MutableLiveData<UserDetails>()
    val bloodProviderUserData get() = _bloodProviderUserData

    fun getBloodProviderData(userID: String) {
        viewModelScope.launch {
            _loadingStatus.value = LoadingStatus.Loading(resourceProvider.getString(R.string.fetching_provider_data))
            val response = databaseRepository.getUserDetails(userID)
            if (response?.isSuccessful == true) {
                _loadingStatus.value = LoadingStatus.Success
                _bloodProviderUserData.value = Gson().fromJson(response.body(), UserDetails::class.java)
            } else {
                _loadingStatus.value = LoadingStatus.Error(GENERIC_ERROR_CODE, GENERIC_ERROR_MESSAGE)
            }
        }
    }

    override fun addAllLiveDataToObservablesList() {
    }
}
