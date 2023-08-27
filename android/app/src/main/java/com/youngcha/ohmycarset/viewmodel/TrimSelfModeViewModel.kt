package com.youngcha.ohmycarset.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.youngcha.ohmycarset.data.model.TrimSelfMode
import com.youngcha.ohmycarset.data.model.TrimSelfModeOption

class TrimSelfModeViewModel : ViewModel() {

    // LiveData & MutableLiveData 정의
    private val _trimSelfModeData = MutableLiveData<TrimSelfMode>()
    val trimSelfModeData: LiveData<TrimSelfMode> = _trimSelfModeData
    val displayItemCount: MutableLiveData<Int> = MutableLiveData(5)

    val filteredOptions: MutableLiveData<List<TrimSelfModeOption>> = MutableLiveData()

    fun showAllItems() {
        displayItemCount.value = trimSelfModeData.value?.options?.size
    }

    fun filterOptionsByTabName(tabName: String) {
        val allOptions = trimSelfModeData.value?.options ?: emptyList()

        val matchedOptions = if (tabName == "전체") {
            showAllItems()
            allOptions
        } else {
            allOptions.filter { option ->
                option.type == tabName
            }
        }

        filteredOptions.value = matchedOptions
    }
}