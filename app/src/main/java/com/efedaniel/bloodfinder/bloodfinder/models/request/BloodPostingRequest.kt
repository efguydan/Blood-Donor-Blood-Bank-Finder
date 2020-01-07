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
    var status: String = ApiKeys.PENDING,
    var bloodPostingRequestID: String? = null,
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
                map["status"] ?: "",
                map["bloodPostingRequestID"]
            )
    }
}
