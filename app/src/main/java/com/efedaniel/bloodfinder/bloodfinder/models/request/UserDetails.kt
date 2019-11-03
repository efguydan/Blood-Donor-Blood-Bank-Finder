package com.efedaniel.bloodfinder.bloodfinder.models.request

data class UserDetails(
    var address: String? = null,
    var firstName: String? = null,
    var gender: String? = null,
    var institutionName: String? = null,
    var lastName: String? = null,
    var localID: String? = null,
    var maritalStatus: String? = null,
    var phoneNumber: String? = null,
    var religion: String? = null,
    var title: String? = null,
    var userType: String? = null,
    var bloodType: String? = null
) {

    fun isBloodDonor() = userType == "Blood Donor"

}