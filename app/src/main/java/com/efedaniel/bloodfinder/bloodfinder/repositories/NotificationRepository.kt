package com.efedaniel.bloodfinder.bloodfinder.repositories

import com.efedaniel.bloodfinder.bloodfinder.apis.NotificationApiService
import com.efedaniel.bloodfinder.bloodfinder.models.request.BloodRequestNotification
import com.google.gson.JsonElement
import retrofit2.Response
import javax.inject.Inject

class NotificationRepository @Inject constructor(private val notificationApiService: NotificationApiService) {

    suspend fun sendBloodRequestNotification(bloodRequestNotification: BloodRequestNotification): Response<JsonElement>? {
        return try {
            notificationApiService.sendBloodRequestNotification(bloodRequestNotification)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
