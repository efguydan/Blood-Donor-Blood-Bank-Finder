package com.efedaniel.bloodfinder.bloodfinder.models

import android.location.Location
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MiniLocation(
    val latitude: String,
    val longitude: String,
    var address: String? = ""
) : Parcelable {

    //Get real directions with road with this https://stackoverflow.com/questions/18310126/get-the-distance-between-two-locations-in-android/18312349#18312349
    fun distanceTo(location: MiniLocation): Float {
        val locationA = Location("Location A")
        locationA.latitude = latitude.toDouble()
        locationA.longitude = longitude.toDouble()

        val locationB = Location("Location B")
        locationB.latitude = location.latitude.toDouble()
        locationB.longitude = location.longitude.toDouble()

        return locationA.distanceTo(locationB)
    }
}
