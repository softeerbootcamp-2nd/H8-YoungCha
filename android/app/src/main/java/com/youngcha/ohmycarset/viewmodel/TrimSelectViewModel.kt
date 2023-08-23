package com.youngcha.ohmycarset.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.youngcha.ohmycarset.enums.TrimType
import com.youngcha.ohmycarset.data.model.TrimCategory
import com.youngcha.ohmycarset.data.model.TrimCategoryState

class TrimSelectViewModel : ViewModel() {
    private val _trimCategoryState = MutableLiveData<TrimCategoryState>()
    val trimCategoryState: LiveData<TrimCategoryState> = _trimCategoryState

    private val _clickedPosition = MutableLiveData<Int>()
    val clickedPosition: LiveData<Int> = _clickedPosition

    init {
        val initialTrimCategories = listOf(
            TrimCategory(TrimType.GUIDE, "Guide Mode", "#나만을 위한 팰리세이드", true),
            TrimCategory(TrimType.SELF, "Exclusive", "#기본에 충실", false),
            TrimCategory(TrimType.SELF, "Le Blanc", "#베스트셀러", false),
            TrimCategory(TrimType.SELF, "Prestige", "#부담없는 고급감", false),
            TrimCategory(TrimType.SELF, "Calligraphy", "#최고를 원한다면", false)
        )
        _trimCategoryState.value = TrimCategoryState(isFirstLoad = true, initialTrimCategories[0], initialTrimCategories)
    }

    fun onItemClicked(clickedTrimCategory: TrimCategory) {
        trimCategoryState.value?.trimCategories?.indexOf(clickedTrimCategory)?.let {
            _clickedPosition.value = it
        }

        val updatedTrims = trimCategoryState.value?.trimCategories?.map { trim ->
            if (trim == clickedTrimCategory) trim.copy(isChecked = true)
            else trim.copy(isChecked = false)
        }
        _trimCategoryState.value = TrimCategoryState(false, clickedTrimCategory, updatedTrims ?: listOf())
    }
}