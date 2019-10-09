package com.efedaniel.bloodfinder.bloodfinder.apis

import android.service.autofill.UserData
import com.efedaniel.bloodfinder.bloodfinder.models.request.UserDetails
import com.google.gson.JsonElement
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET

interface DatabaseApiService {

    @PUT("/users/{user_id}.json")
    suspend fun saveUserDetails(@Path("user_id") userID: String, @Body body: UserDetails): Response<UserDetails>

    @GET("/users/{user_id}.json")
    suspend fun getUserDetails(@Path("user_id") userID: String): Response<JsonElement>
}