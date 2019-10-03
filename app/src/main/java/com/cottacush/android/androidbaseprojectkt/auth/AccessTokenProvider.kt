package com.cottacush.android.androidbaseprojectkt.auth

interface AccessTokenProvider {

    /**
     * Returns an access token. In the event that you don't have a token return null.
     */
    fun token(): String?

    /**
     * Refreshes the token and returns it. This call should be made synchronously.
     * In the event that the token could not be refreshed return null.
     */
    fun refreshToken(): String?
}
