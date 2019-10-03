package com.cottacush.android.androidbaseprojectkt.auth

import okhttp3.Interceptor
import okhttp3.Response

class AccessTokenInterceptor(
    private val tokenProvider: AccessTokenProvider
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val token = tokenProvider.token()
        return if (token == null) {
            chain.proceed(chain.request())
        } else {
            val requestBuilder = chain.request().newBuilder()
            val url = chain.request().url.newBuilder()
                .addQueryParameter(AccessTokenAuthenticator.AUTH_KEY, token).build()
            requestBuilder.url(url)
            return chain.proceed(requestBuilder.build())
        }
    }
}
