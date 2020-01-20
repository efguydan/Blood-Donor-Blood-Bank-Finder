package com.efedaniel.bloodfinder.bloodfinder.home.bloodavailabilty

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.efedaniel.bloodfinder.R
import com.efedaniel.bloodfinder.base.BaseViewModel
import com.efedaniel.bloodfinder.bloodfinder.models.request.UploadBloodAvailabilityRequest
import com.efedaniel.bloodfinder.bloodfinder.models.request.UserDetails
import com.efedaniel.bloodfinder.bloodfinder.models.response.PostResponse
import com.efedaniel.bloodfinder.bloodfinder.repositories.DatabaseRepository
import com.efedaniel.bloodfinder.networkutils.GENERIC_ERROR_CODE
import com.efedaniel.bloodfinder.networkutils.GENERIC_ERROR_MESSAGE
import com.efedaniel.bloodfinder.networkutils.LoadingStatus
import com.efedaniel.bloodfinder.utils.*
import com.google.gson.Gson
import kotlinx.coroutines.launch
import javax.inject.Inject

class BloodAvailabilityViewModel @Inject constructor(
    private val resourceProvider: ResourceProvider,
    private val databaseRepository: DatabaseRepository,
    private val prefsUtils: PrefsUtils
) : BaseViewModel() {

    private val user = prefsUtils.getPrefAsObject(PrefKeys.LOGGED_IN_USER_DATA, UserDetails::class.java)

    private val _bloodPostingList = MutableLiveData<MutableList<UploadBloodAvailabilityRequest>>()
    val bloodPostingList: LiveData<MutableList<UploadBloodAvailabilityRequest>> get() = _bloodPostingList

    private val _hideShimmer = MutableLiveData(false)
    val hideShimmer: LiveData<Boolean> get() = _hideShimmer

    private val _hideDeletingProgress = MutableLiveData(false)
    val hideDeletingProgress: LiveData<Boolean> get() = _hideDeletingProgress

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> get() = _loading

    val fabVisibility = Transformations.map(_bloodPostingList) {
        !(it.size > 0 && user.isBloodDonor())
    }

    fun uploadBloodAvailability(requestBody: UploadBloodAvailabilityRequest) {
        viewModelScope.launch {
            _loadingStatus.value = LoadingStatus.Loading(resourceProvider.getString(R.string.uploading_blood_availability))
            val response = databaseRepository.uploadBloodAvailability(requestBody)
            if (response?.isSuccessful == true) {
                val postResponse = Gson().fromJson(response.body(), PostResponse::class.java)
                uploadBloodAvailabilityID(postResponse.name)
            } else {
                _loadingStatus.value = LoadingStatus.Error(GENERIC_ERROR_CODE, GENERIC_ERROR_MESSAGE)
            }
        }
    }

    private fun uploadBloodAvailabilityID(bloodAvailabilityID: String) {
        viewModelScope.launch {
            val response = databaseRepository.uploadBloodAvailabilityID(bloodAvailabilityID)
            if (response?.isSuccessful == true) {
                getUserBloodAvailability()
            } else {
                _loadingStatus.value = LoadingStatus.Error(GENERIC_ERROR_CODE, GENERIC_ERROR_MESSAGE)
            }
        }
    }

    fun deleteBloodAvailability(bloodAvailabilityID: String) {
        viewModelScope.launch {
            val response = databaseRepository.deleteBloodAvailability(bloodAvailabilityID)
            if (response?.isSuccessful == true) {
                getUserBloodAvailability()
            } else {
                _hideDeletingProgress.value = true
                _loadingStatus.value = LoadingStatus.Error(GENERIC_ERROR_CODE, GENERIC_ERROR_MESSAGE)
            }
        }
    }

    fun getUserBloodAvailability() {
        viewModelScope.launch {
            val response = databaseRepository.getFilteredBloodAvailability(ApiKeys.DONOR_ID, user.localID!!)
            if (response?.isSuccessful == true) {
                val bloodPostings = GsonUtils.fromJson<HashMap<String, UploadBloodAvailabilityRequest>>(response.body())
                _bloodPostingList.value = ArrayList(bloodPostings.values).sortedWith(compareByDescending { it.creationTime }).toMutableList()
                _loadingStatus.value = LoadingStatus.Success
            } else {
                _loadingStatus.value = LoadingStatus.Error(GENERIC_ERROR_CODE, GENERIC_ERROR_MESSAGE)
            }
            _hideShimmer.value = true
        }
    }

    fun hideShimmerDone() { _hideShimmer.value = false }

    fun deletingProgressHidden() { _hideDeletingProgress.value = false }

    override fun addAllLiveDataToObservablesList() {
        observablesList.add(hideShimmer)
        observablesList.add(hideDeletingProgress)
        observablesList.add(fabVisibility)
        observablesList.add(loading)
    }
}
