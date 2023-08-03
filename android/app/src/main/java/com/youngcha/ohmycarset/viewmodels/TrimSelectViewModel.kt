package com.youngcha.ohmycarset.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.youngcha.ohmycarset.models.Trim

class TrimSelectViewModel: ViewModel() {
    private val _currTrim = MutableLiveData<Trim>()
    val currTrim = _currTrim

    fun onItemClick(trim: Trim) {
        _currTrim.value = trim
    }
}