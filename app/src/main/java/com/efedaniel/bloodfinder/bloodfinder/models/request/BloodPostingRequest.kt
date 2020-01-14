package com.efedaniel.bloodfinder.bloodfinder.models.request

import android.os.Parcelable
import com.efedaniel.bloodfinder.utils.ApiKeys
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BloodPostingRequest(
    val bloodAvailabilityID: String,
    val bloodProviderID: String,
    val bloodSeekerID: String,
    val bloodProviderFullName: String,
    val bloodSeekerFullName: String,
    val bloodType: String,
    val billingType: String,
    val seekerType: String,
    val providerType: String,
    var status: String = ApiKeys.PENDING,
    var bloodRequestID: String? = null,
    val creationTime: String = System.currentTimeMillis().toString(),
    var notificationType: String? = null
) : Parcelable {

    companion object {
        fun getBloodPostingFromMap(map: Map<String, String>) = BloodPostingRequest(
                map["bloodAvailabilityID"] ?: "",
                map["bloodProviderID"] ?: "",
                map["bloodSeekerID"] ?: "",
                map["bloodProviderFullName"] ?: "",
                map["bloodSeekerFullName"] ?: "",
                map["bloodType"] ?: "",
                map["billingType"] ?: "",
                map["seekerType"] ?: "",
                map["providerType"] ?: "",
                map["status"] ?: "",
                map["bloodRequestID"],
                map["creationTime"] ?: ""
            )
    }
}
