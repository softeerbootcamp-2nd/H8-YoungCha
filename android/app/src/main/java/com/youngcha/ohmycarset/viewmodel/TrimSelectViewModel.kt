package com.youngcha.ohmycarset.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.youngcha.ohmycarset.model.Trim
import com.youngcha.ohmycarset.model.TrimState

class TrimSelectViewModel: ViewModel() {
    private val _trimState = MutableLiveData<TrimState>()
    val trimState: LiveData<TrimState> = _trimState

    private val _clickedPosition = MutableLiveData<Int>()
    val clickedPosition: LiveData<Int> = _clickedPosition

    fun setTrims(trimsList: List<Trim>) {
        _trimState.value = TrimState(trimsList.first(), trimsList)
    }

    fun onItemClicked(clickedTrim: Trim) {
        trimState.value?.trims?.indexOf(clickedTrim)?.let {
            _clickedPosition.value = it
        }

        val updatedTrims = trimState.value?.trims?.map { trim ->
            if (trim == clickedTrim) trim.copy(isChecked = true)
            else trim.copy(isChecked = false)
        }
        _trimState.value = TrimState(clickedTrim, updatedTrims ?: listOf())
    }

}