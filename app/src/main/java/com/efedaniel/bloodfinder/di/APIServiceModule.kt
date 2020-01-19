package com.efedaniel.bloodfinder.di

import com.efedaniel.bloodfinder.BuildConfig
import com.efedaniel.bloodfinder.auth.AccessTokenInterceptor
import com.efedaniel.bloodfinder.bloodfinder.apis.*
import com.efedaniel.bloodfinder.utils.ApiKeys
import com.google.gson.Gson
import dagger.Lazy
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module(includes = [LocalDataModule::class])
class APIServiceModule {

    @Provides
    @Named("InterceptorService")
    @Singleton
    fun provideExampleServiceHttpClient(
        upstream: OkHttpClient
    ): OkHttpClient {
        return upstream.newBuilder()
            .addInterceptor(AccessTokenInterceptor())
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthAPIService(
        @Named("InterceptorService") client: Lazy<OkHttpClient>,
        gson: Gson
    ): AuthApiService {
        return Retrofit.Builder()
            .baseUrl(ApiKeys.AUTH_BASE_URL)
            .client(client.get())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(AuthApiService::class.java)
    }

    @Provides
    @Singleton
    fun providesDataApiService(
        @Named("InterceptorService") client: Lazy<OkHttpClient>,
        gson: Gson
    ): DatabaseApiService {
        return Retrofit.Builder()
            .baseUrl(ApiKeys.DATABASE_BASE_URL)
            .client(client.get())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(DatabaseApiService::class.java)
    }

    @Provides
    @Singleton
    fun providesNotificationApiService(
        @Named("InterceptorService") client: Lazy<OkHttpClient>,
        gson: Gson
    ): NotificationApiService {
        return Retrofit.Builder()
            .baseUrl(ApiKeys.NOTIFICATION_BASE_URL)
            .client(client.get())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(NotificationApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideGenericOkHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder().addInterceptor(interceptor).build()

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }

    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()

    @Provides
    @Singleton
    fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory =
        GsonConverterFactory.create(gson)
}
