package com.efedaniel.bloodfinder.bloodfinder.home.bloodpostingdetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.efedaniel.bloodfinder.R
import com.efedaniel.bloodfinder.base.BaseViewModel
import com.efedaniel.bloodfinder.bloodfinder.models.request.BloodPostingRequest
import com.efedaniel.bloodfinder.bloodfinder.models.request.BloodRequestNotification
import com.efedaniel.bloodfinder.bloodfinder.models.request.UserDetails
import com.efedaniel.bloodfinder.bloodfinder.models.response.PostResponse
import com.efedaniel.bloodfinder.bloodfinder.repositories.DatabaseRepository
import com.efedaniel.bloodfinder.bloodfinder.repositories.NotificationRepository
import com.efedaniel.bloodfinder.networkutils.GENERIC_ERROR_CODE
import com.efedaniel.bloodfinder.networkutils.GENERIC_ERROR_MESSAGE
import com.efedaniel.bloodfinder.networkutils.LoadingStatus
import com.efedaniel.bloodfinder.utils.ApiKeys
import com.efedaniel.bloodfinder.utils.ResourceProvider
import com.google.gson.Gson
import kotlinx.coroutines.launch
import javax.inject.Inject

class BloodPostingDetailsViewModel @Inject constructor(
    private val resourceProvider: ResourceProvider,
    private val databaseRepository: DatabaseRepository,
    private val notificationRepository: NotificationRepository
) : BaseViewModel() {

    private val _bloodPostingUserDetails = MutableLiveData<UserDetails>()
    val bloodPostingUserDetails get() = _bloodPostingUserDetails

    private val _notificationSentSuccessfully = MutableLiveData(false)
    val notificationSentSuccessfully get() = _notificationSentSuccessfully

    private lateinit var bloodPostingRequest: BloodPostingRequest

    fun getPostingUserDetails(userID: String) {
        viewModelScope.launch {
            _loadingStatus.value = LoadingStatus.Loading(resourceProvider.getString(R.string.fetching_donor_data))
            val response = databaseRepository.getUserDetails(userID)
            if (response?.isSuccessful == true) {
                _loadingStatus.value = LoadingStatus.Success
                _bloodPostingUserDetails.value = Gson().fromJson(response.body(), UserDetails::class.java)
            } else {
                _loadingStatus.value = LoadingStatus.Error(GENERIC_ERROR_CODE, GENERIC_ERROR_MESSAGE)
            }
        }
    }

    fun uploadBloodPostingRequest(bloodPostingRequest: BloodPostingRequest) {
        this.bloodPostingRequest = bloodPostingRequest
        viewModelScope.launch {
            _loadingStatus.value = LoadingStatus.Loading(resourceProvider.getString(R.string.uploading_blood_request))
            val response = databaseRepository.uploadBloodPostingRequest(bloodPostingRequest)
            if (response?.isSuccessful == true) {
                val postResponse = Gson().fromJson(response.body(), PostResponse::class.java)
                uploadBloodPostingRequestID(postResponse.name)
            } else {
                _loadingStatus.value = LoadingStatus.Error(GENERIC_ERROR_CODE, GENERIC_ERROR_MESSAGE)
            }
        }
    }

    private fun uploadBloodPostingRequestID(bloodRequestID: String) {
        viewModelScope.launch {
            val response = databaseRepository.uploadBloodRequestID(bloodRequestID)
            if (response?.isSuccessful == true) {
                bloodPostingRequest.bloodRequestID = bloodRequestID
                sendNotificationToUser()
            } else {
                _loadingStatus.value = LoadingStatus.Error(GENERIC_ERROR_CODE, GENERIC_ERROR_MESSAGE)
            }
        }
    }

    private fun sendNotificationToUser() {
        viewModelScope.launch {
            _loadingStatus.value = LoadingStatus.Loading(resourceProvider.getString(R.string.notifying_blood_provider))
            bloodPostingRequest.notificationType = ApiKeys.REQUEST_NOTIFICATION_TYPE
            val notificationRequest = BloodRequestNotification(bloodPostingRequest,
                bloodPostingUserDetails.value!!.notificationToken!!)
            val response = notificationRepository.sendBloodRequestNotification(notificationRequest)
            if (response?.isSuccessful == true) {
                _loadingStatus.value = LoadingStatus.Success
                _notificationSentSuccessfully.value = true
            } else {
                _loadingStatus.value = LoadingStatus.Error(GENERIC_ERROR_CODE, GENERIC_ERROR_MESSAGE)
            }
        }
    }

    override fun addAllLiveDataToObservablesList() {
        observablesList.add(bloodPostingUserDetails)
        observablesList.add(notificationSentSuccessfully)
    }
}
