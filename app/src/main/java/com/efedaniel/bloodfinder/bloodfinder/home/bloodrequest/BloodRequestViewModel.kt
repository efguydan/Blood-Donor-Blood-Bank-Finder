package com.efedaniel.bloodfinder.bloodfinder.home.bloodrequest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.efedaniel.bloodfinder.R
import com.efedaniel.bloodfinder.base.BaseViewModel
import com.efedaniel.bloodfinder.bloodfinder.models.request.UploadBloodAvailabilityRequest
import com.efedaniel.bloodfinder.bloodfinder.models.request.UserDetails
import com.efedaniel.bloodfinder.bloodfinder.repositories.DatabaseRepository
import com.efedaniel.bloodfinder.networkutils.GENERIC_ERROR_CODE
import com.efedaniel.bloodfinder.networkutils.GENERIC_ERROR_MESSAGE
import com.efedaniel.bloodfinder.networkutils.LoadingStatus
import com.efedaniel.bloodfinder.utils.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class BloodRequestViewModel @Inject constructor(
    private val resourceProvider: ResourceProvider,
    private val databaseRepository: DatabaseRepository,
    private val prefsUtils: PrefsUtils
) : BaseViewModel() {

    private val user = prefsUtils.getPrefAsObject(PrefKeys.LOGGED_IN_USER_DATA, UserDetails::class.java)

    var donorList = mutableListOf<UploadBloodAvailabilityRequest>()
    private var numOfRequests = 0
    private var billingType = ""
    private var kindOfBloodDonor = ""

    private val _moveToBloodResults = MutableLiveData(false)
    val moveToBloodResults: LiveData<Boolean> get() = _moveToBloodResults

    fun getCompatibleBloods(bloodType: String, billingType: String, kindOfBloodDonor: String) {
        _loadingStatus.value = LoadingStatus.Loading(resourceProvider.getString(R.string.searching))
        this.billingType = billingType
        this.kindOfBloodDonor = kindOfBloodDonor
        donorList.clear()
        numOfRequests = Data.bloodCompatibilityMapping.getValue(bloodType).size
        for (type in Data.bloodCompatibilityMapping.getValue(bloodType)) {
            getFilteredBloodAvailability(type)
        }
    }

    private fun getFilteredBloodAvailability(type: String) {
        viewModelScope.launch {
            val response = databaseRepository.getFilteredBloodAvailability(ApiKeys.BLOOD_TYPE, type)
            if (response?.isSuccessful == true) {
                val bloodPostings = GsonUtils.fromJson<HashMap<String, UploadBloodAvailabilityRequest>>(response.body())
                donorList.addAll(ArrayList(bloodPostings.values))
                Timber.d(donorList.size.toString())
                numOfRequests--
                if (numOfRequests == 0) {
                    triggerNextStep()
                }
            } else {
                numOfRequests--
                if (numOfRequests == 0) {
                    triggerNextStep()
                }
                _loadingStatus.value = LoadingStatus.Error(GENERIC_ERROR_CODE, GENERIC_ERROR_MESSAGE)
            }
        }
    }

    private fun triggerNextStep() {
        Timber.d(donorList.size.toString())

        val donorsToRemove = mutableListOf<UploadBloodAvailabilityRequest>()
        for (bloodPosting in donorList) {

            // Remove blood postings created by the current user
            if (bloodPosting.donorName == user.fullName()) donorsToRemove.add(bloodPosting)

            // Remove the billing types that weren't asked for by the user
            else if (this.billingType.toLowerCase() != "any" && bloodPosting.billingType != this.billingType) donorsToRemove.add(bloodPosting)

            // Remove the kind of donors that were not asked for by the user
            else if (this.kindOfBloodDonor.toLowerCase() != "any" && bloodPosting.donorType != this.kindOfBloodDonor) donorsToRemove.add(bloodPosting)
        }
        donorList.removeAll(donorsToRemove)

        // After all filters, if the list is not empty, then we move to result fragment
        if (donorList.isNotEmpty()) {

            // Sort the blood donors according to the distance to the current User
            donorList = donorList.sortedWith(compareBy { it.location.distanceTo(user.location!!) }).toMutableList()

            _moveToBloodResults.value = true
            _loadingStatus.value = LoadingStatus.Success
        } else {
            _loadingStatus.value = LoadingStatus.Error(GENERIC_ERROR_CODE, resourceProvider.getString(R.string.blood_provider_not_found))
        }
    }

    override fun addAllLiveDataToObservablesList() {
        observablesList.add(moveToBloodResults)
    }

    fun moveToBloodResultsDone() { _moveToBloodResults.value = false }
}
