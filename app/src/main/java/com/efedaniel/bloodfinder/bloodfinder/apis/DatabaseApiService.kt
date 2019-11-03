package com.efedaniel.bloodfinder.bloodfinder.apis

import com.efedaniel.bloodfinder.bloodfinder.models.request.UploadBloodAvailabilityRequest
import com.efedaniel.bloodfinder.bloodfinder.models.request.UserDetails
import com.google.gson.JsonElement
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Body

interface DatabaseApiService {

    @PUT("/users/{user_id}.json")
    suspend fun saveUserDetails(@Path("user_id") userID: String, @Body body: UserDetails): Response<UserDetails>

    @GET("/users/{user_id}.json")
    suspend fun getUserDetails(@Path("user_id") userID: String): Response<JsonElement>

    @POST("/bloodAvailability.json")
    suspend fun uploadBloodAvailability(@Body body: UploadBloodAvailabilityRequest): Response<JsonElement>

    @PUT("/bloodAvailability/{blood_availability_id}/bloodAvailabilityID.json")
    suspend fun uploadBloodAvailabilityID(@Path("blood_availability_id") bloodAvailabilityID: String,
                                          @Body body: String): Response<JsonElement>

    @GET("/bloodAvailability.json")
    suspend fun getAllBloodPostings(): Response<JsonElement>

    @GET("/bloodAvailability/{blood_availability_id}.json")
    suspend fun getBloodPosting(): Response<JsonElement>
}