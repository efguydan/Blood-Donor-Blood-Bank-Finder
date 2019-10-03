package com.cottacush.android.androidbaseprojectkt.sample.apis

import com.cottacush.android.androidbaseprojectkt.sample.NetworkBreedModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ExampleApiService {
    companion object {
        const val ENDPOINT = "https://api.thecatapi.com/"
    }

    @GET("v1/breeds")
    suspend fun getCatBreeds(@Query("limit") limit: Int): Response<List<NetworkBreedModel>>
}
