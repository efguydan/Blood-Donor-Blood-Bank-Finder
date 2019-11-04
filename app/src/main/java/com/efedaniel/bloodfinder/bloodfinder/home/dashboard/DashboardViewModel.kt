package com.efedaniel.bloodfinder.bloodfinder.home.dashboard

import com.efedaniel.bloodfinder.R
import com.efedaniel.bloodfinder.base.BaseViewModel
import com.efedaniel.bloodfinder.bloodfinder.models.request.UserDetails
import com.efedaniel.bloodfinder.utils.Data
import com.efedaniel.bloodfinder.utils.PrefKeys
import com.efedaniel.bloodfinder.utils.PrefsUtils
import com.efedaniel.bloodfinder.utils.ResourceProvider
import javax.inject.Inject

class DashboardViewModel @Inject constructor(
    prefsUtils: PrefsUtils,
    resourceProvider: ResourceProvider
): BaseViewModel() {

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

    override fun addAllLiveDataToObservablesList() {
    }

}