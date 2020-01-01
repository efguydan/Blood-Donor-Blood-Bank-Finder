package com.efedaniel.bloodfinder.bloodfinder.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Location(
    val latitude: String,
    val longitude: String,
    var address: String? = ""
) : Parcelable