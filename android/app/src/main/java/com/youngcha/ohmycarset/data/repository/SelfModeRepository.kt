package com.youngcha.ohmycarset.data.repository

import com.youngcha.ohmycarset.data.api.SelfModeApiService

class SelfModeRepository(private val apiService: SelfModeApiService) {

    suspend fun getPowerTrain(id: Int) = apiService.getPowerTrain(id)

    suspend fun getDrivingSystem(id: Int) = apiService.getDrivingSystem(id)

    suspend fun getBodyType(id: Int) = apiService.getBodyType(id)

    suspend fun getExteriorColor(id: Int) = apiService.getExteriorColor(id)

    suspend fun getInteriorColor(id: Int, exteriorColorId: Int) = apiService.getInteriorColor(id, exteriorColorId)

    suspend fun getWheel(id: Int) = apiService.getWheel(id)

    suspend fun getOptions(id: Int) = apiService.getOptions(id)

}