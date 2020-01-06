package com.efedaniel.bloodfinder.bloodfinder.models.request

data class ResetPasswordRequest(
    val email: String,
    val requestType: String = "PASSWORD_RESET"
)
