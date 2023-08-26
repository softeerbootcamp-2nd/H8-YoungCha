package com.youngcha.ohmycarset.data.api

import com.youngcha.ohmycarset.data.repository.GuideModeRepository
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

// RetrofitClient.kt
object RetrofitClient {
    private const val BASE_URL = "https://api.youngcha.team"

    // OkHttpClient 인스턴스 생성 및 timeout 설정
    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)  // 읽기 timeout 60초
            .connectTimeout(60, TimeUnit.SECONDS)  // 연결 timeout 60초
            .build()
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)  // Retrofit에 OkHttpClient 설정
            .build()
    }

    val selfModeApi: SelfModeApiService by lazy {
        retrofit.create(SelfModeApiService::class.java)
    }

    val guideModeApi: GuideModeApiService by lazy {
        retrofit.create(GuideModeApiService::class.java)
    }

    val categoriesApi: CategoriesApiService by lazy {
        retrofit.create(CategoriesApiService::class.java)
    }

    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}
