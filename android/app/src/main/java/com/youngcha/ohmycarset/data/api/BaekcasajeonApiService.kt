package com.youngcha.ohmycarset.data.api

import com.youngcha.ohmycarset.data.dto.BaekcasajeonDTO
import retrofit2.http.GET

interface BaekcasajeonApiService {

    @GET("/car-make/dictionary")
    suspend fun getBaekcasajeon(): BaekcasajeonDTO
}