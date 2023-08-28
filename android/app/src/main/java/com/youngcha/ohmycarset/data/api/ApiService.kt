package com.youngcha.ohmycarset.data.api

import com.youngcha.ohmycarset.data.model.Dictionary
import com.youngcha.ohmycarset.data.dto.OptionCategory
import com.youngcha.ohmycarset.data.dto.TrimDefaultOption
import com.youngcha.ohmycarset.data.dto.TrimMainData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {
    @GET("/car-make/dictionary")
    suspend fun getCarMakeDictionary(): Response<Dictionary>

    @GET("/cars/{id}/details")
    suspend fun getMainPageData(@Path("id") id: Int): Response<TrimMainData>

    @GET("/trims/{id}/default-components")
    suspend fun getTrimBasicOption(
        @Path("id") id: Int,
        @Query("categoryId") categoryId: Int,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Response<TrimDefaultOption>

    @GET("/categories")
    suspend fun getOptionCategory():Response<OptionCategory>

}
