package com.efedaniel.bloodfinder.bloodfinder.home.requesthistory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.efedaniel.bloodfinder.R
import com.efedaniel.bloodfinder.base.BaseViewModel
import com.efedaniel.bloodfinder.bloodfinder.models.request.BloodPostingRequest
import com.efedaniel.bloodfinder.bloodfinder.models.request.UserDetails
import com.efedaniel.bloodfinder.bloodfinder.repositories.DatabaseRepository
import com.efedaniel.bloodfinder.networkutils.GENERIC_ERROR_CODE
import com.efedaniel.bloodfinder.networkutils.GENERIC_ERROR_MESSAGE
import com.efedaniel.bloodfinder.networkutils.LoadingStatus
import com.efedaniel.bloodfinder.utils.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class RequestHistoryViewModel @Inject constructor(
    private val resourceProvider: ResourceProvider,
    private val databaseRepository: DatabaseRepository,
    private val prefsUtils: PrefsUtils
): BaseViewModel() {

    private val user = prefsUtils.getPrefAsObject(PrefKeys.LOGGED_IN_USER_DATA, UserDetails::class.java)

    private val _requestHistoryList = MutableLiveData<MutableList<BloodPostingRequest>>()
    val requestHistoryList: LiveData<MutableList<BloodPostingRequest>> get() = _requestHistoryList

    fun getUserRequestHistory() {
        viewModelScope.launch {
            _loadingStatus.value = LoadingStatus.Loading(resourceProvider.getString(R.string.getting_request_history))
            val response = databaseRepository.getUserBloodPostingHistory(ApiKeys.BLOOD_SEEKER_ID, user.localID!!)
            if (response?.isSuccessful == true) {
                val bloodPostings = GsonUtils.fromJson<HashMap<String, BloodPostingRequest>>(response.body())
                _requestHistoryList.value = ArrayList(bloodPostings.values).sortedWith(compareByDescending { it.creationTime }).toMutableList()
                _loadingStatus.value = LoadingStatus.Success
            } else {
                _loadingStatus.value = LoadingStatus.Error(GENERIC_ERROR_CODE, GENERIC_ERROR_MESSAGE)
            }
        }
    }

    override fun addAllLiveDataToObservablesList() {

    }
}