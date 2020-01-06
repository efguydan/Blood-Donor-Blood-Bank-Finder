package com.efedaniel.bloodfinder.bloodfinder.notifications.bloodPostingRequest

import com.efedaniel.bloodfinder.base.BaseViewModel
import com.efedaniel.bloodfinder.bloodfinder.repositories.DatabaseRepository
import com.efedaniel.bloodfinder.bloodfinder.repositories.NotificationRepository
import com.efedaniel.bloodfinder.utils.ResourceProvider
import javax.inject.Inject

class BloodPostingRequestViewModel @Inject constructor(
    private val resourceProvider: ResourceProvider,
    private val databaseRepository: DatabaseRepository,
    private val notificationRepository: NotificationRepository
) : BaseViewModel() {

    override fun addAllLiveDataToObservablesList() {
    }

}