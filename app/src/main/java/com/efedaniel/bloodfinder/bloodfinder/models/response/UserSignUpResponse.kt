package com.efedaniel.bloodfinder.bloodfinder.models.response

data class UserSignUpResponse(
    val email: String,
    val expiresIn: String,
    val idToken: String,
    val kind: String,
    val localId: String,
    val refreshToken: String,
    val displayName: String,
    val registered: String
)
