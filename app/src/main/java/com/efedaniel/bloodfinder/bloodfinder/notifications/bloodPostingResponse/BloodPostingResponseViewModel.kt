package com.efedaniel.bloodfinder.bloodfinder.notifications.bloodPostingResponse

import com.efedaniel.bloodfinder.base.BaseViewModel
import com.efedaniel.bloodfinder.bloodfinder.repositories.DatabaseRepository
import com.efedaniel.bloodfinder.utils.ResourceProvider
import javax.inject.Inject

class BloodPostingResponseViewModel @Inject constructor(
    private val resourceProvider: ResourceProvider,
    private val databaseRepository: DatabaseRepository
) : BaseViewModel() {

    override fun addAllLiveDataToObservablesList() {
    }

}