package com.efedaniel.bloodfinder.bloodfinder.home.dashboard

import androidx.lifecycle.viewModelScope
import com.efedaniel.bloodfinder.R
import com.efedaniel.bloodfinder.base.BaseViewModel
import com.efedaniel.bloodfinder.bloodfinder.models.request.UserDetails
import com.efedaniel.bloodfinder.bloodfinder.repositories.DatabaseRepository
import com.efedaniel.bloodfinder.networkutils.Result
import com.efedaniel.bloodfinder.utils.Data
import com.efedaniel.bloodfinder.utils.PrefKeys
import com.efedaniel.bloodfinder.utils.PrefsUtils
import com.efedaniel.bloodfinder.utils.ResourceProvider
import kotlinx.coroutines.launch
import javax.inject.Inject

class DashboardViewModel @Inject constructor(
    private val prefsUtils: PrefsUtils,
    private val resourceProvider: ResourceProvider,
    private val databaseRepository: DatabaseRepository
) : BaseViewModel() {

    private val userDetails: UserDetails = prefsUtils.getPrefAsObject(PrefKeys.LOGGED_IN_USER_DATA, UserDetails::class.java)

    var userName = ""

    var actionList = listOf<String>()

    init {
        userName = when (userDetails.userType) {
            "Blood Donor" -> "${userDetails.title} ${userDetails.firstName} ${userDetails.lastName}"
            "Hospital", "Private Blood Bank" -> userDetails.institutionName ?: "Hospital / Private Blood Bank"
            else -> resourceProvider.getString(R.string.invalid_user_type)
        }
        actionList = when (userDetails.userType) {
            "Blood Donor" -> Data.bloodDonorActionList
            "Hospital" -> Data.hospitalActionList
            "Private Blood Bank" -> Data.bloodBankActionList
            else -> listOf(resourceProvider.getString(R.string.invalid_user_type))
        }
    }

    fun subscribeLoggedInUserToNotification() {
        if (prefsUtils.doesContain(PrefKeys.DEVICE_NOTIFICATION_TOKEN)) {
            viewModelScope.launch {
                when (databaseRepository.saveUserNotificationToken(userDetails.localID!!, prefsUtils.getString(PrefKeys.DEVICE_NOTIFICATION_TOKEN, "")!!)) {
                    is Result.Success -> {
                        prefsUtils.putBoolean(PrefKeys.IS_NOTIFICATION_SUBSCRIBED, true)
                    }
                    is Result.Error -> {
                        prefsUtils.putBoolean(PrefKeys.IS_NOTIFICATION_SUBSCRIBED, false)
                    }
                }
            }
        }
    }

    override fun addAllLiveDataToObservablesList() {
    }
}
