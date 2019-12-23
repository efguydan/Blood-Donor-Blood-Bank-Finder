package com.efedaniel.bloodfinder.bloodfinder.models

data class Location(
    val latitude: String,
    val longitude: String,
    val address: String = ""
)