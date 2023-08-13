package com.youngcha.ohmycarset.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.youngcha.ohmycarset.model.tag.Age
import com.youngcha.ohmycarset.model.tag.Gender
import com.youngcha.ohmycarset.model.tag.Strength
import com.youngcha.ohmycarset.model.tag.Important
import com.youngcha.ohmycarset.model.tag.Use

class UserTagViewModel : ViewModel() {

    private val _ageList = MutableLiveData<List<Age>>()
    val ageList: LiveData<List<Age>> = _ageList

    private val _genderList = MutableLiveData<List<Gender>>()
    val genderList: LiveData<List<Gender>> = _genderList

    private val _strengthList = MutableLiveData<List<Strength>>()
    val strengthList: LiveData<List<Strength>> = _strengthList

    private val _importantList = MutableLiveData<List<Important>>()
    val importantList: LiveData<List<Important>> = _importantList

    private val _useList = MutableLiveData<List<Use>>()
    val useList: LiveData<List<Use>> = _useList


    private var selectedAge: Age? = null
    private var selectedGender: Gender? = null
    private var selectedStrength: Strength? = null
    private var selectedImportant: Important? = null
    private var selectedUse: Use? = null

    init {
        initData()
    }

    private fun initData() {
        val tempAgeList = listOf(
            Age("20대"),
            Age("30대"),
            Age("40대"),
            Age("50대"),
            Age("60대"),
            Age("70대 이상")
        )
        val tempGenderList = listOf(
            Gender("여성"),
            Gender("남성"),
            Gender("선택 안함")
        )
        val tempStrength = listOf(
            Strength("주행력"),
            Strength("소음"),
            Strength("효율"),
            Strength("파워")
        )
        val tempImportant = listOf(
            Important("디자인"),
            Important("차량 보호"),
            Important("온도 조절"),
            Important("건강"),
            Important("신기술"),
            Important("안전")
        )
        val tempUse = listOf(
            Use("차박"),
            Use("가족여행")
        )

        _ageList.value = tempAgeList
        _genderList.value = tempGenderList
        _strengthList.value = tempStrength
        _importantList.value = tempImportant
        _useList.value = tempUse
    }

    fun updateSelectedAge(age: Age) {
        selectedAge?.isSelected = false
        age.isSelected = true
        selectedAge = age
        _ageList.value = _ageList.value
    }

    fun updateSelectedGender(gender: Gender) {
        selectedGender?.isSelected = false
        gender.isSelected = true
        selectedGender = gender
        _ageList.value = _ageList.value
    }

    fun updateSelectedStrength(strength: Strength) {
        selectedStrength = strength
        _strengthList.value = _strengthList.value
    }

    fun updateSelectedImportant(important: Important) {
        selectedImportant = important
        _importantList.value = _importantList.value
    }

    fun updateSelectedUse(use: Use) {
        selectedUse = use
        _useList.value = _useList.value
    }

    fun resetSelection() {
        selectedStrength?.isSelected = false
        selectedImportant?.isSelected = false
        selectedUse?.isSelected = false
    }
}
