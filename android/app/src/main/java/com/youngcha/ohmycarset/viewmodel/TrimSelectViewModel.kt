package com.youngcha.ohmycarset.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.youngcha.ohmycarset.enums.TrimType
import com.youngcha.ohmycarset.data.model.TrimCategory
import com.youngcha.ohmycarset.data.model.TrimCategoryState
import com.youngcha.ohmycarset.data.dto.OptionCategory
import com.youngcha.ohmycarset.data.dto.TrimDefaultOption
import com.youngcha.ohmycarset.data.dto.TrimMainData
import com.youngcha.ohmycarset.data.api.RetrofitClient
import kotlinx.coroutines.launch

class TrimSelectViewModel : ViewModel() {
    private val _trimCategoryState = MutableLiveData<TrimCategoryState>()
    val trimCategoryState: LiveData<TrimCategoryState> = _trimCategoryState

    private val _clickedPosition = MutableLiveData<Int?>()
    val clickedPosition: LiveData<Int?> = _clickedPosition

    private val _data = MutableLiveData<TrimMainData.Data?>()

    private val _model = MutableLiveData<TrimMainData.Data.ModelName>()
    val model: LiveData<TrimMainData.Data.ModelName> = _model

    private val _guide = MutableLiveData<TrimMainData.Data.Guide>()
    val guide: LiveData<TrimMainData.Data.Guide> = _guide

    private val _trim = MutableLiveData<TrimMainData.Data.Trim>()
    val trim: LiveData<TrimMainData.Data.Trim> = _trim

    private val _category = MutableLiveData<OptionCategory.Data?>()

    private val _trimDefaultOption = MutableLiveData<List<TrimDefaultOption.Data.Content>?>()
    val trimDefaultOption: LiveData<List<TrimDefaultOption.Data.Content>?> = _trimDefaultOption

    val displayItemCount: MutableLiveData<Int> = MutableLiveData(5)
    val filteredOptions: MutableLiveData<List<TrimDefaultOption.Data.Content>?> =
        MutableLiveData()

    init {
        initAPI()
    }

    private fun initAPI() {
        viewModelScope.launch {
            try {
                val responseTrimData = RetrofitClient.apiService.getMainPageData(1)
                val responseCategory = RetrofitClient.apiService.getOptionCategory()
                val responseDefaultOption = RetrofitClient.apiService.getTrimBasicOption(2, 0, 1, 100)

                if (responseTrimData.isSuccessful) {
                    val data = responseTrimData.body()?.data
                    if (data != null) {
                        _guide.value = data.guide
                        _data.value = data
                        setTrimCategory()
                    }
                }
                if (responseCategory.isSuccessful) {
                    val data = responseCategory.body()?.data
                    if (data != null) {
                        _category.value = data
                    } else {
                        Log.e("API", "Data is null")
                    }
                }
                if (responseDefaultOption.isSuccessful) {
                    val data = responseDefaultOption.body()?.data?.contents
                    _trimDefaultOption.value = data
                }
            } catch (e: Exception) {
                Log.e("API", "API call failed with exception: ${e.message}")
            }
        }
    }
    private fun setTrimCategory() {
        val trimsSize = _data.value?.trims?.size
        val newTrimCategoryList = mutableListOf<TrimCategory>() // 새로운 리스트 생성

        newTrimCategoryList.add(TrimCategory(TrimType.GUIDE, "Guide Mode", "나에게 딱맞는 구성으로", true))
        for (i in 0 until (trimsSize ?: 0)) { // null이 아니라면 trimsSize, 아니면 0
            _data.value?.trims?.get(i)?.let { trim ->
                newTrimCategoryList.add(
                    TrimCategory(
                        TrimType.SELF,
                        _data.value?.trims!![i].name,
                        trim.hashTag,
                        false
                    )
                )
            }
        }
        _trimCategoryState.value =
            TrimCategoryState(isFirstLoad = true, newTrimCategoryList[0], newTrimCategoryList)
    }

    fun onItemClicked(clickedTrimCategory: TrimCategory) {
        val clickedIndex = trimCategoryState.value?.trimCategories?.indexOf(clickedTrimCategory)

        if (clickedIndex != null && clickedIndex > 0) {
            val data = _data.value
            if (data != null && clickedIndex <= data.trims.size) {
                _clickedPosition.value = clickedIndex
                _guide.value = data.guide
                _trim.value = data.trims[clickedIndex - 1]
            }
        }
        val updatedTrims = trimCategoryState.value?.trimCategories?.map { trim ->
            if (trim == clickedTrimCategory) trim.copy(isChecked = true)
            else trim.copy(isChecked = false)
        }
        _trimCategoryState.value =
            TrimCategoryState(false, clickedTrimCategory, updatedTrims ?: listOf())
    }

    fun filterOptionsByTabName(trimId: Int,categoryId: Int) {
        viewModelScope.launch {
            try {
                val responseTrimDefaultOption = RetrofitClient.apiService.getTrimBasicOption(trimId, categoryId, 1, 100)
                if (responseTrimDefaultOption.isSuccessful) {
                    val data = responseTrimDefaultOption.body()?.data?.contents
                    _trimDefaultOption.value = data
                } else {
                    Log.e("API Option", "API call failed with code: ${responseTrimDefaultOption.code()}")
                }
            } catch (e: Exception) {
                Log.e("API Option", "API call failed with exception: ${e.message}")
            }
        }
    }
    fun showAllItems() {
        displayItemCount.value = 100
    }

    fun showFiveItems(){
        displayItemCount.value = 5
    }
}