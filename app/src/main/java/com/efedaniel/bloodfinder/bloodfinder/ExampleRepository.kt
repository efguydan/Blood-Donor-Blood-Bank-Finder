package com.efedaniel.bloodfinder.bloodfinder

import com.efedaniel.bloodfinder.bloodfinder.apis.AuthApiService
import javax.inject.Inject

class ExampleRepository @Inject constructor(
    private val exampleApiService: AuthApiService
)
