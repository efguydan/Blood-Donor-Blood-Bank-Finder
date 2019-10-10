package com.efedaniel.bloodfinder.bloodfinder.home.dashboard

import com.efedaniel.bloodfinder.base.BaseViewModel
import com.efedaniel.bloodfinder.bloodfinder.models.request.UserDetails
import com.efedaniel.bloodfinder.utils.PrefKeys
import com.efedaniel.bloodfinder.utils.PrefsUtils
import javax.inject.Inject

class DashboardViewModel @Inject constructor(
    private val prefsUtils: PrefsUtils
): BaseViewModel() {

    private val userDetails: UserDetails

    var userTypeString = ""

    init {
        userDetails = prefsUtils.getPrefAsObject(PrefKeys.LOGGED_IN_USER_DATA, UserDetails::class.java)
        userTypeString = "Dashboard For ${userDetails.userType}"
    }

    override fun addAllLiveDataToObservablesList() {

    }

}