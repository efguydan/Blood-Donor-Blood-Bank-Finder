package com.cottacush.android.androidbaseprojectkt.sample.apis

import com.cottacush.android.androidbaseprojectkt.utils.PrefsUtils
import com.cottacush.android.androidbaseprojectkt.auth.AccessTokenProvider
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AccessTokenProviderImpl @Inject constructor(
    val exampleAPIAuthService: ExampleAPIAuthService,
    val prefsUtils: PrefsUtils
) : AccessTokenProvider {

    /*The access token is provided here as a constant string for sample purpose. Normally, you'd want to make a synchronous
    network call to get the token with exampleAPIAuthService in the refreshToken() method and save it with prefsUtils.
    then access the token from prefsUtils inside the token() method.*/
    // TODO implement a the AccessTokenProviderImpl as described above

    private val apiKey = "1fcac9b1-9150-4fdd-80ef-15232ff441d5"

    override fun token(): String? {
        return apiKey
    }

    override fun refreshToken(): String? {
        return apiKey
    }
}
