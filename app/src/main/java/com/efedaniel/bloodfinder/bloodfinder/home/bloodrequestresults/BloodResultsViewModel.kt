package com.efedaniel.bloodfinder.bloodfinder.home.bloodrequestresults

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.efedaniel.bloodfinder.base.BaseViewModel
import com.efedaniel.bloodfinder.bloodfinder.models.request.UploadBloodAvailabilityRequest
import javax.inject.Inject

class BloodResultsViewModel @Inject constructor() : BaseViewModel() {

    private val _bloodResultsList = MutableLiveData<List<UploadBloodAvailabilityRequest>>()
    val bloodResultsList: LiveData<List<UploadBloodAvailabilityRequest>> get() = _bloodResultsList

    fun setBloodResults(list: List<UploadBloodAvailabilityRequest>) {
        _bloodResultsList.value = list
    }

    override fun addAllLiveDataToObservablesList() {
    }
}
