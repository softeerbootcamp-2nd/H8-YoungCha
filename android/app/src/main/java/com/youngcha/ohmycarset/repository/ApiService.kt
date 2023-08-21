package com.youngcha.ohmycarset.repository

import com.youngcha.ohmycarset.model.Dictionary
import com.youngcha.ohmycarset.model.TrimMainData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    // CarMake Dictionary Endpoint
    @GET("/car-make/dictionary")
    suspend fun getCarMakeDictionary(): Response<Dictionary>

    @GET("/cars/{id}/details")
    suspend fun getMainPageData(@Path("id") id:Int ) :Response<TrimMainData>


}
