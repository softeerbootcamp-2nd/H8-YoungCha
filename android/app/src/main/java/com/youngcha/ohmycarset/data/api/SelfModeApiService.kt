package com.youngcha.ohmycarset.data.api

import com.youngcha.ohmycarset.data.dto.ComponentDTO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SelfModeApiService {

    @GET("/car-make/{id}/self/power-train")
    suspend fun getPowerTrain(@Path("id") id: Int): ComponentDTO

    @GET("/car-make/{id}/self/driving-system")
    suspend fun getDrivingSystem(@Path("id") id: Int): ComponentDTO

    @GET("/car-make/{id}/self/body-type")
    suspend fun getBodyType(@Path("id") id: Int): ComponentDTO

    @GET("/car-make/{id}/self/exterior-color")
    suspend fun getExteriorColor(@Path("id") id: Int): ComponentDTO

    @GET("/car-make/{id}/self/interior-color")
    suspend fun getInteriorColor(
        @Path("id") id: Int,
        @Query("exteriorColorId") exteriorColorId: Int
    ): ComponentDTO

    @GET("/car-make/{id}/self/wheel")
    suspend fun getWheel(@Path("id") id: Int): ComponentDTO

    @GET("/car-make/{id}/self/options")
    suspend fun getOptions(@Path("id") id: Int): ComponentDTO
}