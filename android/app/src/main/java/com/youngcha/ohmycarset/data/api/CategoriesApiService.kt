package com.youngcha.ohmycarset.data.api

import com.youngcha.ohmycarset.data.dto.CategoryDTO
import retrofit2.http.GET

interface CategoriesApiService {
    @GET("/categories")
    suspend fun getCategories(): CategoryDTO
}