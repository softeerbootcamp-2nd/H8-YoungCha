package com.youngcha.ohmycarset.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// RetrofitClient.kt
object RetrofitClient {
    private const val BASE_URL = "https://api.youngcha.team"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val selfModeApi: SelfModeApiService by lazy {
        retrofit.create(SelfModeApiService::class.java)
    }

    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}
