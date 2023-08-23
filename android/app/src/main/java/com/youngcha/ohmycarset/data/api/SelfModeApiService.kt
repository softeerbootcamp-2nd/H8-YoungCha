package com.youngcha.ohmycarset.data.api

import com.youngcha.ohmycarset.data.dto.Option
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SelfModeApiService {

    @GET("/car-make/{id}/self/power-train")
    suspend fun getPowerTrain(@Path("id") id: String): Option

    @GET("/car-make/{id}/self/driving-system")
    suspend fun getDrivingSystem(@Path("id") id: String): Option

    @GET("/car-make/{id}/self/body-type")
    fun getBodyType(@Path("id") id: Int): Option

    @GET("/car-make/{id}/self/exterior-color")
    fun getExteriorColor(@Path("id") id: Int): Option

    @GET("/car-make/{id}/self/interior-color")
    fun getInteriorColor(
        @Path("id") id: Int,
        @Query("exteriorColorId") exteriorColorId: Int
    ): Option

    @GET("/car-make/{id}/self/wheel")
    fun getWheel(@Path("id") id: Int): Option

    @GET("/car-make/{id}/self/options")
    fun getOptions(@Path("id") id: Int): Option
}