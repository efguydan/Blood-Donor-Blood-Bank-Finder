package com.efedaniel.bloodfinder.bloodfinder.models.request

import com.efedaniel.bloodfinder.bloodfinder.models.MiniLocation
import com.efedaniel.bloodfinder.extensions.getCharAt

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
    var bloodType: String? = null,
    var location: MiniLocation? = null,
    val notificationToken: String? = null
) {
    fun isBloodDonor() = userType == "Blood Donor"

    fun fullName() = if (isBloodDonor()) "$title $firstName $lastName" else institutionName ?: "User"

    fun getInitials(): String {
        return when (userType) {
            "Blood Donor" -> "${firstName!!.getCharAt(0)}${lastName!!.getCharAt(0)}"
            else -> {
                val array = institutionName!!.split(" ")
                var initials = array[0].getCharAt(0)
                if (array.size > 1) initials += array[1].getCharAt(0)
                initials
            }
        }
    }
}
