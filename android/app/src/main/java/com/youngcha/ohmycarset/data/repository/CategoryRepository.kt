package com.youngcha.ohmycarset.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.youngcha.ohmycarset.data.api.CategoriesApiService
import com.youngcha.ohmycarset.data.dto.Category

class CategoryRepository(private val categoryAPIService: CategoriesApiService) {

    private val _mainCategories = MutableLiveData<List<String>>()
    val mainCategories: LiveData<List<String>> = _mainCategories

    private val _subCategories = MutableLiveData<List<String>>()
    val subCategories: LiveData<List<String>> = _subCategories

    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> = _categories

    suspend fun getCategories() {
        try {
            val response = categoryAPIService.getCategories()
            if (response.message == "success") {
                // Filter out categories with ids 1-6, extract their names, and sort them
                val sortedCategoryNames = response.data.categories
                    .filter { it.id in 1..5 }
                    .sortedBy { it.id }
                    .map { it.name }

                // "옵션 선택" and "견적 내기" items
                val mainCategoriesName = sortedCategoryNames + listOf("휠 선택", "옵션 선택", "견적 내기")

                val subCategoriesName = response.data.categories
                    .filter { it.id in 7..10 }  // Here we change to get IDs from 7 to 10
                    .sortedBy { it.id }
                    .map { it.name }

                val sortedCategories = response.data.categories
                    .filter { it.id in 7..10 }  // And here as well
                    .sortedBy { it.id }

                _mainCategories.postValue(mainCategoriesName)
                _subCategories.postValue(subCategoriesName)
                _categories.postValue(sortedCategories)
            } else {
                _mainCategories.postValue(emptyList())
                _subCategories.postValue(emptyList())
            }
        } catch (e: Exception) {
            _mainCategories.postValue(emptyList())
            _subCategories.postValue(emptyList())
        }
    }

}