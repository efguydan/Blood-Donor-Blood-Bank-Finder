package com.efedaniel.bloodfinder.bloodfinder.models.request

import com.google.gson.annotations.SerializedName

data class BloodRequestNotification(
    val data: BloodPostingRequest,
    @SerializedName("to")
    val destinationToken: String
)
