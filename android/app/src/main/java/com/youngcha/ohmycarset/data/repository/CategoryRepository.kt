package com.youngcha.ohmycarset.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.youngcha.ohmycarset.data.api.CategoriesApiService
import com.youngcha.ohmycarset.data.dto.Category

class CategoryRepository(private val categoryAPIService: CategoriesApiService) {

    companion object {
        private val MAIN_CATEGORY_IDS = 1..5
        private val SUB_CATEGORY_IDS = 7..10
    }

    private val _mainCategories = MutableLiveData<List<String>>()
    val mainCategories: LiveData<List<String>> = _mainCategories

    private val _subCategories = MutableLiveData<List<String>>()
    val subCategories: LiveData<List<String>> = _subCategories

    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> = _categories

    suspend fun getCategories() {
        val categories = fetchCategories() ?: return

        val mainCategoriesName = categories.filter { it.id in MAIN_CATEGORY_IDS }
            .sortedBy { it.id }
            .map { it.name } + listOf("휠 선택", "옵션 선택", "견적 내기")

        val subCategoriesName = categories.filter { it.id in SUB_CATEGORY_IDS }
            .sortedBy { it.id }
            .map { it.name }

        _mainCategories.postValue(mainCategoriesName)
        _subCategories.postValue(subCategoriesName)
        _categories.postValue(categories.filter { it.id in SUB_CATEGORY_IDS })
    }

    suspend fun getAllSubCategories(): List<Category>? {
        val categories = fetchCategories() ?: return null
        return categories.filter { it.id in SUB_CATEGORY_IDS }.sortedBy { it.id }
    }

    private suspend fun fetchCategories(): List<Category>? {
        return try {
            val response = categoryAPIService.getCategories()
            if (response.message == "success") {
                response.data.categories
            } else {
                _mainCategories.postValue(emptyList())
                _subCategories.postValue(emptyList())
                null
            }
        } catch (e: Exception) {
            // Here you can log the exception if needed
            _mainCategories.postValue(emptyList())
            _subCategories.postValue(emptyList())
            null
        }
    }
}