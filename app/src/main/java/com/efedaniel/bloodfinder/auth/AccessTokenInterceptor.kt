package com.efedaniel.bloodfinder.auth

import com.efedaniel.bloodfinder.utils.ApiKeys
import okhttp3.Interceptor
import okhttp3.Response

class AccessTokenInterceptor(
    private val tokenProvider: AccessTokenProvider
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        val url = chain.request().url.newBuilder()
            .addQueryParameter("key", ApiKeys.WEB_API_KEY).build()
        requestBuilder.url(url)
        return chain.proceed(requestBuilder.build())
    }
}
