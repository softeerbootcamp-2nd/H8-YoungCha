package com.youngcha.ohmycarset.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.youngcha.ohmycarset.model.PowerTrain
import com.youngcha.ohmycarset.R

class CarComponentViewModel : ViewModel() {
    private val _powerTrainData = MutableLiveData<PowerTrain>()
    val powerTrainData: LiveData<PowerTrain> = _powerTrainData

    init {
        loadPowerTrain()
    }

    private fun loadPowerTrain() {
        val tempPowerTrain = PowerTrain(
            imageURL = R.drawable.img_onboarding,
            rate = "구매자의 63%가 선택한",
            name = "디젤 2.2",
            price = "+1,480,000원"
        )
        _powerTrainData.value = tempPowerTrain
    }
}