package com.youngcha.ohmycarset.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.youngcha.ohmycarset.data.api.CategoriesApiService

class CategoryRepository(private val categoryAPIService: CategoriesApiService) {

    private val _mainCategories = MutableLiveData<List<String>>()
    val mainCategories: LiveData<List<String>> = _mainCategories

    private val _subCategories = MutableLiveData<List<String>>()
    val subCategories: LiveData<List<String>> = _subCategories

    suspend fun getCategories() {
        try {
            val response = categoryAPIService.getCategories()
            if (response.message == "success") {
                // Filter out categories with ids 1-6, extract their names, and sort them
                val sortedCategoryNames = response.data.categories
                    .filter { it.id in 1..6 }
                    .sortedBy { it.id }
                    .map { it.name }

                // "옵션 선택" and "견적 내기" items
                val mainCategoriesName = sortedCategoryNames + listOf("옵션 선택", "견적 내기")

                val subCategoriesName = response.data.categories
                    .filterNot { it.id in 1..6 } // We use filterNot here to get categories excluding ids 1-6
                    .sortedBy { it.id }
                    .map { it.name }

                _mainCategories.value = mainCategoriesName
                _subCategories.value = subCategoriesName
            } else {
                _mainCategories.value = emptyList()
                _subCategories.value = emptyList()
            }
        } catch (e: Exception) {
            _mainCategories.value = emptyList()
            _subCategories.value = emptyList()
        }
    }
}