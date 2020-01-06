package com.efedaniel.bloodfinder.bloodfinder.models.request

data class UserSignUpRequest(
    val email: String,
    val password: String,
    val returnSecureToken: Boolean = true
)
