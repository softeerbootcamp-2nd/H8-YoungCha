package com.youngcha.ohmycarset.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.youngcha.ohmycarset.data.model.Baekcasajeon
import com.youngcha.ohmycarset.data.repository.BaekcasajeonRepository
import kotlinx.coroutines.launch

class BaekcasajeonViewModel(private val baekcasajeonRepository: BaekcasajeonRepository): ViewModel() {

    val baekcasajeonSuccess: LiveData<Boolean> = baekcasajeonRepository.baekcasajeonSuccess
    val baekcasajeon: LiveData<List<Baekcasajeon>> = baekcasajeonRepository.baekcasajeon

    init {
        getBaekcasajeon()
    }

    private fun getBaekcasajeon() {
        viewModelScope.launch {
            baekcasajeonRepository.getBaekcasajeon()
        }
    }
}