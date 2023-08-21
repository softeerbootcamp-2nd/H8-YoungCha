package com.youngcha.ohmycarset.repository

import com.youngcha.ohmycarset.model.Dictionary
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    // CarMake Dictionary Endpoint
    @GET("/car-make/dictionary")
    suspend fun getCarMakeDictionary(): Response<Dictionary>

}
