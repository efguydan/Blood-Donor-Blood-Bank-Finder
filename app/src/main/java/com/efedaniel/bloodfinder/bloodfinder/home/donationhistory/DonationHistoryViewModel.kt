package com.efedaniel.bloodfinder.bloodfinder.home.donationhistory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
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

class DonationHistoryViewModel @Inject constructor(
    private val resourceProvider: ResourceProvider,
    private val databaseRepository: DatabaseRepository,
    private val prefsUtils: PrefsUtils
) : BaseViewModel() {

    private val user = prefsUtils.getPrefAsObject(PrefKeys.LOGGED_IN_USER_DATA, UserDetails::class.java)

    private val _donationHistoryList = MutableLiveData<MutableList<BloodPostingRequest>>()
    val donationHistoryList: LiveData<MutableList<BloodPostingRequest>> get() = _donationHistoryList

    val emptyViewVisibility = Transformations.map(_donationHistoryList) { it.isEmpty() }

    fun getUserDonationHistory() {
        if (_donationHistoryList.value != null) return
        viewModelScope.launch {
            _loadingStatus.value = LoadingStatus.Loading(resourceProvider.getString(R.string.fetching_donation_history))
            val response = databaseRepository.getUserBloodPostingHistory(ApiKeys.BLOOD_PROVIDER_ID, user.localID!!)
            if (response?.isSuccessful == true) {
                val bloodPostings = GsonUtils.fromJson<HashMap<String, BloodPostingRequest>>(response.body())
                _donationHistoryList.value = ArrayList(bloodPostings.values).sortedWith(compareByDescending { it.creationTime }).toMutableList()
                _loadingStatus.value = LoadingStatus.Success
            } else {
                _loadingStatus.value = LoadingStatus.Error(GENERIC_ERROR_CODE, GENERIC_ERROR_MESSAGE)
            }
        }
    }

    override fun addAllLiveDataToObservablesList() {
        observablesList.add(donationHistoryList)
        observablesList.add(emptyViewVisibility)
    }
}
