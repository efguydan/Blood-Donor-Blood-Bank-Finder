package com.efedaniel.bloodfinder.bloodfinder.apis

import com.efedaniel.bloodfinder.bloodfinder.models.MiniLocation
import com.efedaniel.bloodfinder.bloodfinder.models.request.BloodPostingRequest
import com.efedaniel.bloodfinder.bloodfinder.models.request.UploadBloodAvailabilityRequest
import com.efedaniel.bloodfinder.bloodfinder.models.request.UserDetails
import com.google.gson.JsonElement
import retrofit2.Response
import retrofit2.http.*

interface DatabaseApiService {

    @PUT("/users/{user_id}.json")
    suspend fun saveUserDetails(@Path("user_id") userID: String, @Body body: UserDetails): Response<UserDetails>

    @PUT("/users/{user_id}/location.json")
    suspend fun saveUserLocation(@Path("user_id") userID: String, @Body body: MiniLocation): Response<MiniLocation>

    @GET("/users/{user_id}.json")
    suspend fun getUserDetails(@Path("user_id") userID: String): Response<JsonElement>

    @POST("/bloodAvailability.json")
    suspend fun uploadBloodAvailability(@Body body: UploadBloodAvailabilityRequest): Response<JsonElement>

    @PUT("/bloodAvailability/{blood_availability_id}/bloodAvailabilityID.json")
    suspend fun uploadBloodAvailabilityID(
        @Path("blood_availability_id") bloodAvailabilityID: String,
        @Body body: String
    ): Response<JsonElement>

    @DELETE("/bloodAvailability/{blood_availability_id}.json")
    suspend fun deleteBloodAvailability(@Path("blood_availability_id") bloodAvailabilityID: String): Response<JsonElement>

    @GET("/bloodAvailability.json")
    suspend fun getFilteredBloodAvailability(@Query("orderBy") filterKey: String, @Query("equalTo") filterValue: String): Response<JsonElement>

    @PUT("/users/{user_id}/notificationToken.json")
    suspend fun saveUserNotificationToken(@Path("user_id") userID: String, @Body token: String): Response<String>

    @POST("/bloodRequests.json")
    suspend fun uploadBloodPostingRequest(@Body body: BloodPostingRequest): Response<JsonElement>

    @PUT("/bloodRequests/{blood_request_id}/bloodRequestID.json")
    suspend fun uploadBloodRequestID(
        @Path("blood_request_id") bloodRequestID: String,
        @Body body: String
    ): Response<JsonElement>

    @PUT("/bloodRequests/{blood_request_id}/status.json")
    suspend fun updateBloodRequestStatus(@Path("blood_request_id") bloodRequestID: String, @Body status: String): Response<String>

    @GET("/bloodRequests.json")
    suspend fun getUserBloodPostingHistory(@Query("orderBy") filterKey: String, @Query("equalTo") filterValue: String): Response<JsonElement>
}
