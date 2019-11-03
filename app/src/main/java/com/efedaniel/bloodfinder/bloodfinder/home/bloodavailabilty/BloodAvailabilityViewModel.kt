package com.efedaniel.bloodfinder.bloodfinder.home.bloodavailabilty

import androidx.lifecycle.viewModelScope
import com.efedaniel.bloodfinder.R
import com.efedaniel.bloodfinder.base.BaseViewModel
import com.efedaniel.bloodfinder.bloodfinder.models.request.UploadBloodAvailabilityRequest
import com.efedaniel.bloodfinder.bloodfinder.models.response.PostResponse
import com.efedaniel.bloodfinder.bloodfinder.repositories.DatabaseRepository
import com.efedaniel.bloodfinder.networkutils.GENERIC_ERROR_CODE
import com.efedaniel.bloodfinder.networkutils.GENERIC_ERROR_MESSAGE
import com.efedaniel.bloodfinder.networkutils.LoadingStatus
import com.efedaniel.bloodfinder.utils.PrefsUtils
import com.efedaniel.bloodfinder.utils.ResourceProvider
import com.google.gson.Gson
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class BloodAvailabilityViewModel @Inject constructor(
    private val resourceProvider: ResourceProvider,
    private val databaseRepository: DatabaseRepository
): BaseViewModel() {

    fun uploadBloodAvailability(requestBody: UploadBloodAvailabilityRequest) {
        viewModelScope.launch {
            _loadingStatus.value = LoadingStatus.Loading(resourceProvider.getString(R.string.uplading_blood_availability))
            val response = databaseRepository.uploadBloodAvailability(requestBody)
            if (response?.isSuccessful == true) {
                val postResponse = Gson().fromJson(response.body(), PostResponse::class.java)
                uploadBloodAvailabilityID(postResponse.name)
                _loadingStatus.value = LoadingStatus.Success
            } else {
                _loadingStatus.value = LoadingStatus.Error(GENERIC_ERROR_CODE, GENERIC_ERROR_MESSAGE)
            }
        }
    }

    private fun uploadBloodAvailabilityID(bloodAvailabilityID: String) {
        viewModelScope.launch {
            val response = databaseRepository.uploadBloodAvailabilityID(bloodAvailabilityID)
            if (response?.isSuccessful == true) {
                //TODO Come and collect the response
                _loadingStatus.value = LoadingStatus.Success
            } else {
                _loadingStatus.value = LoadingStatus.Error(GENERIC_ERROR_CODE, GENERIC_ERROR_MESSAGE)
            }
        }
    }

    override fun addAllLiveDataToObservablesList() {

    }

}