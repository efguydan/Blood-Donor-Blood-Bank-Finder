package com.efedaniel.bloodfinder.bloodfinder.apis

import com.efedaniel.bloodfinder.bloodfinder.models.request.ResetPasswordRequest
import com.efedaniel.bloodfinder.bloodfinder.models.request.UserSignUpRequest
import com.efedaniel.bloodfinder.bloodfinder.models.response.ResetPasswordResponse
import com.efedaniel.bloodfinder.bloodfinder.models.response.UserSignUpResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {

    @POST("v1/accounts:signUp")
    suspend fun signUpUser(@Body user: UserSignUpRequest): Response<UserSignUpResponse>

    @POST("v1/accounts:signInWithPassword")
    suspend fun signInUser(@Body user: UserSignUpRequest): Response<UserSignUpResponse>

    @POST("v1/accounts:sendOobCode")
    suspend fun resetUserPassword(@Body resetPasswordRequest: ResetPasswordRequest): Response<ResetPasswordResponse>
}
