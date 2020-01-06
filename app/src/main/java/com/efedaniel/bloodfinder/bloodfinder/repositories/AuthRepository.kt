package com.efedaniel.bloodfinder.bloodfinder.repositories

import com.efedaniel.bloodfinder.bloodfinder.apis.AuthApiService
import com.efedaniel.bloodfinder.bloodfinder.models.request.ResetPasswordRequest
import com.efedaniel.bloodfinder.bloodfinder.models.request.UserSignUpRequest
import com.efedaniel.bloodfinder.bloodfinder.models.response.ResetPasswordResponse
import com.efedaniel.bloodfinder.bloodfinder.models.response.UserSignUpResponse
import com.efedaniel.bloodfinder.networkutils.GENERIC_ERROR_CODE
import com.efedaniel.bloodfinder.networkutils.GENERIC_ERROR_MESSAGE
import com.efedaniel.bloodfinder.networkutils.getAPIResult
import com.efedaniel.bloodfinder.networkutils.Result
import java.lang.Exception
import javax.inject.Inject

class AuthRepository @Inject constructor(private val authApiService: AuthApiService) {

    suspend fun signUpUser(email: String, password: String): Result<UserSignUpResponse> {
        return try {
            getAPIResult(authApiService.signUpUser(UserSignUpRequest(email, password)))
        } catch (e: Exception) {
            Result.Error(GENERIC_ERROR_CODE, GENERIC_ERROR_MESSAGE)
        }
    }

    suspend fun signInUser(email: String, password: String): Result<UserSignUpResponse> {
        return try {
            getAPIResult(authApiService.signInUser(UserSignUpRequest(email, password)))
        } catch (e: Exception) {
            Result.Error(GENERIC_ERROR_CODE, GENERIC_ERROR_MESSAGE)
        }
    }

    suspend fun resetUserPassword(email: String): Result<ResetPasswordResponse> {
        return try {
            getAPIResult(authApiService.resetUserPassword(ResetPasswordRequest(email)))
        } catch (e: Exception) {
            Result.Error(GENERIC_ERROR_CODE, GENERIC_ERROR_MESSAGE)
        }
    }
}
