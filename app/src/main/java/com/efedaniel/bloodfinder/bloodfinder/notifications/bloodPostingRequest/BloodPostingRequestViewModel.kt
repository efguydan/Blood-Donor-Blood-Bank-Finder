package com.efedaniel.bloodfinder.bloodfinder.notifications.bloodPostingRequest

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.efedaniel.bloodfinder.R
import com.efedaniel.bloodfinder.base.BaseViewModel
import com.efedaniel.bloodfinder.bloodfinder.models.request.BloodPostingRequest
import com.efedaniel.bloodfinder.bloodfinder.models.request.BloodRequestNotification
import com.efedaniel.bloodfinder.bloodfinder.models.request.UserDetails
import com.efedaniel.bloodfinder.bloodfinder.repositories.DatabaseRepository
import com.efedaniel.bloodfinder.bloodfinder.repositories.NotificationRepository
import com.efedaniel.bloodfinder.networkutils.GENERIC_ERROR_CODE
import com.efedaniel.bloodfinder.networkutils.GENERIC_ERROR_MESSAGE
import com.efedaniel.bloodfinder.networkutils.LoadingStatus
import com.efedaniel.bloodfinder.networkutils.Result
import com.efedaniel.bloodfinder.utils.ApiKeys
import com.efedaniel.bloodfinder.utils.ResourceProvider
import com.google.gson.Gson
import kotlinx.coroutines.launch
import javax.inject.Inject

class BloodPostingRequestViewModel @Inject constructor(
    private val resourceProvider: ResourceProvider,
    private val databaseRepository: DatabaseRepository,
    private val notificationRepository: NotificationRepository
) : BaseViewModel() {

    private val _bloodSeekerUserData = MutableLiveData<UserDetails>()
    val bloodSeekerUserData get() = _bloodSeekerUserData

    private val _notificationSentSuccessfully = MutableLiveData<String>()
    val notificationSentSuccessfully get() = _notificationSentSuccessfully

    fun getBloodSeekerData(userID: String) {
        viewModelScope.launch {
            _loadingStatus.value = LoadingStatus.Loading(resourceProvider.getString(R.string.fetching_seeker_data))
            val response = databaseRepository.getUserDetails(userID)
            if (response?.isSuccessful == true) {
                _loadingStatus.value = LoadingStatus.Success
                _bloodSeekerUserData.value = Gson().fromJson(response.body(), UserDetails::class.java)
            } else {
                _loadingStatus.value = LoadingStatus.Error(GENERIC_ERROR_CODE, GENERIC_ERROR_MESSAGE)
            }
        }
    }

    fun updateBloodRequestStatus(bloodPosting: BloodPostingRequest, status: String) {
        viewModelScope.launch {
            _loadingStatus.value = LoadingStatus.Loading(resourceProvider.getString(when (status) {
                ApiKeys.ACCEPTED -> R.string.accepting_request
                else -> R.string.declining_request
            }))
            when (databaseRepository.updateBloodRequestStatus(bloodPosting.bloodRequestID!!, status)) {
                is Result.Success -> {
                    bloodPosting.status = status
                    if (bloodPosting.providerType == "Blood Donor" && status == ApiKeys.ACCEPTED) {
                        // Delete Blood Posting for blood donors
                        deleteBloodAvailability(bloodPosting, status)
                    } else {
                        sendNotificationToBloodSeeker(bloodPosting, status)
                    }
                }
                is Result.Error -> {
                    _loadingStatus.value = LoadingStatus.Error(GENERIC_ERROR_CODE, GENERIC_ERROR_MESSAGE)
                }
            }
        }
    }

    private fun deleteBloodAvailability(bloodPosting: BloodPostingRequest, status: String) {
        viewModelScope.launch {
            val response = databaseRepository.deleteBloodAvailability(bloodPosting.bloodAvailabilityID)
            if (response?.isSuccessful == true) {
                sendNotificationToBloodSeeker(bloodPosting, status)
            } else {
                _loadingStatus.value = LoadingStatus.Error(GENERIC_ERROR_CODE, GENERIC_ERROR_MESSAGE)
            }
        }
    }

    private fun sendNotificationToBloodSeeker(bloodPosting: BloodPostingRequest, status: String) {
        viewModelScope.launch {
            _loadingStatus.value = LoadingStatus.Loading(resourceProvider.getString(R.string.notifying_blood_seeker))
            bloodPosting.notificationType = ApiKeys.ANSWER_NOTIFICATION_TYPE
            val notificationRequest = BloodRequestNotification(bloodPosting, bloodSeekerUserData.value!!.notificationToken!!)
            val response = notificationRepository.sendBloodRequestNotification(notificationRequest)
            if (response?.isSuccessful == true) {
                _loadingStatus.value = LoadingStatus.Success
                _notificationSentSuccessfully.value = status
            } else {
                _loadingStatus.value = LoadingStatus.Error(GENERIC_ERROR_CODE, GENERIC_ERROR_MESSAGE)
            }
        }
    }

    override fun addAllLiveDataToObservablesList() {
        observablesList.add(bloodSeekerUserData)
        observablesList.add(notificationSentSuccessfully)
    }
}
