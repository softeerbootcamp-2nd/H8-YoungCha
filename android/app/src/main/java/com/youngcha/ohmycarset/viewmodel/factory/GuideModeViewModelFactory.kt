package com.youngcha.ohmycarset.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.youngcha.ohmycarset.data.model.GuideParam
import com.youngcha.ohmycarset.data.repository.CategoryRepository
import com.youngcha.ohmycarset.data.repository.GuideModeRepository
import com.youngcha.ohmycarset.viewmodel.CarCustomizationViewModel
import com.youngcha.ohmycarset.viewmodel.GuideModeViewModel

class GuideModeViewModelFactory(
    private val guideModeRepository: GuideModeRepository,
    private val categoryRepository: CategoryRepository,
    private val guideParam: GuideParam
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GuideModeViewModel::class.java)) {
            return GuideModeViewModel(guideModeRepository, categoryRepository, guideParam) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
