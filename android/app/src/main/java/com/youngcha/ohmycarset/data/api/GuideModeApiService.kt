package com.youngcha.ohmycarset.data.api

import com.youngcha.ohmycarset.data.dto.ComponentDTO
import com.youngcha.ohmycarset.data.dto.GuideModeDTO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GuideModeApiService {

    @GET("/car-make/{id}/guide/power-train")
    suspend fun getPowerTrainGuide(
        @Path("id") id: Int,
        @Query("gender") gender: Int,
        @Query("age") age: Int,
        @Query("keyword1Id") keyword1Id: Int,
        @Query("keyword2Id") keyword2Id: Int,
        @Query("keyword3Id") keyword3Id: Int
    ): GuideModeDTO

    @GET("/car-make/{id}/guide/driving-system")
    suspend fun getDrivingSystem(
        @Path("id") id: Int,
        @Query("gender") gender: Int,
        @Query("age") age: Int,
        @Query("keyword1Id") keyword1Id: Int,
        @Query("keyword2Id") keyword2Id: Int,
        @Query("keyword3Id") keyword3Id: Int
    ): GuideModeDTO

    @GET("/car-make/{id}/guide/body-type")
    suspend fun getBodyTypeGuide(
        @Path("id") id: Int,
        @Query("gender") gender: Int,
        @Query("age") age: Int,
        @Query("keyword1Id") keyword1Id: Int,
        @Query("keyword2Id") keyword2Id: Int,
        @Query("keyword3Id") keyword3Id: Int
    ): GuideModeDTO

    @GET("/car-make/{id}/guide/exterior-color")
    suspend fun getExteriorColorGuide(
        @Path("id") id: Int,
        @Query("gender") gender: Int,
        @Query("age") age: Int,
        @Query("keyword1Id") keyword1Id: Int,
        @Query("keyword2Id") keyword2Id: Int,
        @Query("keyword3Id") keyword3Id: Int
    ): GuideModeDTO

    @GET("/car-make/{id}/guide/interior-color")
    suspend fun getInteriorColorGuide(
        @Path("id") id: Int,
        @Query("gender") gender: Int,
        @Query("age") age: Int,
        @Query("keyword1Id") keyword1Id: Int,
        @Query("keyword2Id") keyword2Id: Int,
        @Query("keyword3Id") keyword3Id: Int,
        @Query("exteriorColorId") exteriorColorId: Int
    ): GuideModeDTO

    @GET("/car-make/{id}/guide/wheel")
    suspend fun getWheelGuide(
        @Path("id") id: Int,
        @Query("gender") gender: Int,
        @Query("age") age: Int,
        @Query("keyword1Id") keyword1Id: Int,
        @Query("keyword2Id") keyword2Id: Int,
        @Query("keyword3Id") keyword3Id: Int,
        @Query("exteriorColorId") exteriorColorId: Int
    ): GuideModeDTO

    @GET("/car-make/{id}/guide/options")
    suspend fun getOptionsGuide(
        @Path("id") id: Int,
        @Query("gender") gender: Int,
        @Query("age") age: Int,
        @Query("keyword1Id") keyword1Id: Int,
        @Query("keyword2Id") keyword2Id: Int,
        @Query("keyword3Id") keyword3Id: Int
    ): GuideModeDTO
}