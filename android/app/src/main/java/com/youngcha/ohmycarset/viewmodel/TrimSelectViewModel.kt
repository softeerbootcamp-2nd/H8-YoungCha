package com.youngcha.ohmycarset.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.youngcha.ohmycarset.enums.TrimType
import com.youngcha.ohmycarset.model.Trim
import com.youngcha.ohmycarset.model.TrimState

class TrimSelectViewModel : ViewModel() {
    private val _trimState = MutableLiveData<TrimState>()
    val trimState: LiveData<TrimState> = _trimState

    private val _clickedPosition = MutableLiveData<Int>()
    val clickedPosition: LiveData<Int> = _clickedPosition

    init {
        val initialTrims = listOf(
            Trim(TrimType.GUIDE, "Guide Mode", "#나만을 위한 팰리세이드", true),
            Trim(TrimType.SELF, "Exclusive", "#기본에 충실", false),
            Trim(TrimType.SELF, "Le Blanc", "#베스트셀러", false),
            Trim(TrimType.SELF, "Prestige", "#부담없는 고급감", false),
            Trim(TrimType.SELF, "Calligraphy", "#최고를 원한다면", false)
        )
        _trimState.value = TrimState(isFirstLoad = true, initialTrims[0], initialTrims)
    }

    fun onItemClicked(clickedTrim: Trim) {
        trimState.value?.trims?.indexOf(clickedTrim)?.let {
            _clickedPosition.value = it
        }

        val updatedTrims = trimState.value?.trims?.map { trim ->
            if (trim == clickedTrim) trim.copy(isChecked = true)
            else trim.copy(isChecked = false)
        }
        _trimState.value = TrimState(false, clickedTrim, updatedTrims ?: listOf())
    }
}