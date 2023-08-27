package com.youngcha.ohmycarset.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide.init
import com.youngcha.ohmycarset.data.model.Baekcasajeon
import com.youngcha.ohmycarset.data.repository.BaekcasajeonRepository
import kotlinx.coroutines.launch

class BaekcasajeonViewModel(private val baekcasajeonRepository: BaekcasajeonRepository): ViewModel() {

    val baekcasajeonSuccess: LiveData<Boolean> = baekcasajeonRepository.baekcasajeonSuccess
    val baekcasajeon: LiveData<List<Baekcasajeon>> = baekcasajeonRepository.baekcasajeon

    val baekcasajeonState: MutableLiveData<Int> = MutableLiveData(0)

    init {
        getBaekcasajeon()
    }

    private fun getBaekcasajeon() {
        viewModelScope.launch {
            baekcasajeonRepository.getBaekcasajeon()
        }
    }

    fun setBaekcasajeonState() {
        baekcasajeonState.value = if (baekcasajeonState.value == 0) 1 else 0
    }
}