package com.efedaniel.bloodfinder.auth

import com.efedaniel.bloodfinder.utils.ApiKeys
import okhttp3.Interceptor
import okhttp3.Response

class AccessTokenInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        if (chain.request().url.toString().contains(ApiKeys.AUTH_BASE_URL)) {
            val url = chain.request().url.newBuilder()
                .addQueryParameter("key", ApiKeys.WEB_API_KEY).build()
            requestBuilder.url(url)
        } else if (chain.request().url.toString().contains(ApiKeys.NOTIFICATION_BASE_URL)) {
            requestBuilder.addHeader(ApiKeys.AUTHORIZATION, ApiKeys.NOTIFICATION_SERVER_KEY)
        }
        return chain.proceed(requestBuilder.build())
    }
}
