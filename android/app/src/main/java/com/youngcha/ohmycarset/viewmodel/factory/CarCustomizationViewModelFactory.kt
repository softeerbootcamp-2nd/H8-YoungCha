package com.youngcha.ohmycarset.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.youngcha.ohmycarset.data.repository.CategoryRepository
import com.youngcha.ohmycarset.data.repository.GuideModeRepository
import com.youngcha.ohmycarset.data.repository.SelfModeRepository
import com.youngcha.ohmycarset.viewmodel.CarCustomizationViewModel

class CarCustomizationViewModelFactory (
    private val selfModeRepository: SelfModeRepository,
    private val guideModeRepository: GuideModeRepository,
    private val categoryRepository: CategoryRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CarCustomizationViewModel::class.java)) {
            return CarCustomizationViewModel(selfModeRepository, guideModeRepository, categoryRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
