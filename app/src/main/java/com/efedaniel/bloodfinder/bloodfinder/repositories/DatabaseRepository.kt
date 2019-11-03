package com.efedaniel.bloodfinder.bloodfinder.repositories

import com.efedaniel.bloodfinder.bloodfinder.apis.DatabaseApiService
import com.efedaniel.bloodfinder.bloodfinder.models.request.UploadBloodAvailabilityRequest
import com.efedaniel.bloodfinder.bloodfinder.models.request.UserDetails
import com.efedaniel.bloodfinder.networkutils.GENERIC_ERROR_CODE
import com.efedaniel.bloodfinder.networkutils.GENERIC_ERROR_MESSAGE
import com.efedaniel.bloodfinder.networkutils.getAPIResult
import com.efedaniel.bloodfinder.networkutils.Result
import com.google.gson.JsonElement
import retrofit2.Response
import javax.inject.Inject

class DatabaseRepository @Inject constructor(private val databaseApiService: DatabaseApiService) {

    suspend fun saveUserDetails(userID: String, userDetails: UserDetails): Result<UserDetails> {
        return try {
            getAPIResult(databaseApiService.saveUserDetails(userID, userDetails))
        } catch (e: Exception) {
            Result.Error(GENERIC_ERROR_CODE, GENERIC_ERROR_MESSAGE)
        }
    }

    suspend fun getUserDetails(userID: String): Response<JsonElement>? {
        return try {
            databaseApiService.getUserDetails(userID)
        } catch (e: Exception) {
            null
        }
    }

    suspend fun uploadBloodAvailability(body: UploadBloodAvailabilityRequest): Response<JsonElement>? {
        return try {
            databaseApiService.uploadBloodAvailability(body)
        } catch (e: java.lang.Exception) {
            null
        }
    }

    suspend fun uploadBloodAvailabilityID(bloodAvailabilityID: String): Response<JsonElement>? {
        return try {
            databaseApiService.uploadBloodAvailabilityID(bloodAvailabilityID, bloodAvailabilityID)
        } catch (e: java.lang.Exception) {
            null
        }
    }

    suspend fun getFilteredBloodAvailability(key: String, value: String): Response<JsonElement>? {
        return try {
            databaseApiService.getFilteredBloodAvailability("\"$key\"", "\"$value\"")
        } catch (e: java.lang.Exception) {
            null
        }
    }
}