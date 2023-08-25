package com.youngcha.ohmycarset.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.youngcha.ohmycarset.data.dto.Category
import com.youngcha.ohmycarset.data.model.GuideParam
import com.youngcha.ohmycarset.data.model.car.Car
import com.youngcha.ohmycarset.data.repository.CategoryRepository
import com.youngcha.ohmycarset.data.repository.GuideModeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GuideModeViewModel(private val repository: GuideModeRepository, private val categoryRepository: CategoryRepository, private val guideParam: GuideParam): ViewModel() {

    private val _selectedCar = MutableLiveData<Car>()
    val selectedCar: LiveData<Car> = _selectedCar

    val categories = MutableLiveData<List<Category>?>()

    init {
        viewModelScope.launch(Dispatchers.IO) {

            val fetchedCategories = categoryRepository.getAllCategories()
            Log.d("로그", fetchedCategories.toString())
            fetchedCategories?.let {
                repository.fetchAllGuideDataAndSetCar(guideParam, it)
                withContext(Dispatchers.Main) {
                    // 메인 스레드에서 LiveData 업데이트
                    categories.value = it
                    _selectedCar.value = repository.car.value
                }
            } ?: run {
                Log.d("로그", "실패")
            }
        }
    }
}