package com.efedaniel.bloodfinder.bloodfinder.models.request

import com.efedaniel.bloodfinder.utils.ApiKeys

data class BloodPostingRequest(
    val bloodAvailabilityID: String,
    val bloodProviderID: String,
    val bloodSeekerID: String,
    val bloodProviderFullName: String,
    val bloodSeekerFullName: String,
    val bloodType: String,
    val status: String = ApiKeys.PENDING,
    var bloodPostingRequestID: String? = null,
    var notificationType: String? = null
)