package com.youngcha.ohmycarset.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.youngcha.ohmycarset.data.repository.BaekcasajeonRepository
import com.youngcha.ohmycarset.viewmodel.BaekcasajeonViewModel

class BaekcasajeonFactory(
    private val baekcasajeonRepository: BaekcasajeonRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BaekcasajeonViewModel::class.java)) {
            return BaekcasajeonViewModel(baekcasajeonRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}