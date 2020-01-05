package com.efedaniel.bloodfinder.bloodfinder.repositories

import com.efedaniel.bloodfinder.bloodfinder.apis.NotificationApiService
import com.efedaniel.bloodfinder.bloodfinder.models.request.NotificationRequest
import com.google.gson.JsonElement
import retrofit2.Response
import javax.inject.Inject

class NotificationRepository @Inject constructor(private val notificationApiService: NotificationApiService) {

    suspend fun sendNotification(notificationRequest: NotificationRequest): Response<JsonElement>? {
        return try {
            notificationApiService.sendNotification(notificationRequest)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

}