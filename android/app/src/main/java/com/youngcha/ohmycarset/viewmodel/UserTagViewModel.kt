package com.youngcha.ohmycarset.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.youngcha.ohmycarset.model.tag.Tag

class UserTagViewModel : ViewModel() {

    private val _ageList = MutableLiveData<List<Tag>>()
    val ageList: LiveData<List<Tag>> = _ageList

    private val _genderList = MutableLiveData<List<Tag>>()
    val genderList: LiveData<List<Tag>> = _genderList

    private val _strengthList = MutableLiveData<List<Tag>>()
    val strengthList: LiveData<List<Tag>> = _strengthList

    private val _importantList = MutableLiveData<List<Tag>>()
    val importantList: LiveData<List<Tag>> = _importantList

    private val _useList = MutableLiveData<List<Tag>>()
    val useList: LiveData<List<Tag>> = _useList

    private val selectedTag = MutableLiveData<Tag?>()
    
    fun onTagLongItemClick(tag: Tag) {
        when (tag.tagType) {
            "Age" -> updateSelectedAge(tag)
            "Gender" -> updateSelectedGender(tag)
        }
        selectedTag.value = tag
    }

    fun onTagShortItemClick(tag: Tag) {
        Log.d("Tag", "Clicked")
        when (tag.tagType) {
            "Strength" -> {
                updateSelectedStrength(tag)
                Log.d("Tag", "Strength")
                Log.d("Strength", cnt.toString())
                Log.d("Strength", isChange.value.toString())

            }

            "Important" -> {
                updateSelectedImportant(tag)
                Log.d("Tag", "Important")
                Log.d("Important", cnt.toString())
                Log.d("Strength", isChange.value.toString())

            }

            "Use" -> {
                updateSelectedUse(tag)
                Log.d("Tag", "Use")
                Log.d("Use", cnt.toString())
                Log.d("Strength", isChange.value.toString())

            }
        }
        selectedTag.value = tag
    }


    private var selectedAge: Tag? = null
    private var selectedGender: Tag? = null
    private var selectedStrength: Tag? = null
    private var selectedImportant: Tag? = null
    private var selectedUse: Tag? = null

    init {
        initData()
    }

    private fun initData() {
        val tempAgeList = listOf(
            Tag("Age", "20대", isSelected = false),
            Tag("Age", "30대", isSelected = false),
            Tag("Age", "40대", isSelected = false),
            Tag("Age", "50대", isSelected = false),
            Tag("Age", "60대", isSelected = false),
            Tag("Age", "70대 이상", isSelected = false)
        )
        val tempGenderList = listOf(
            Tag("Gender", "여성", isSelected = false),
            Tag("Gender", "남성", isSelected = false),
            Tag("Gender", "선택 안함", isSelected = false)
        )
        val tempStrength = listOf(
            Tag("Strength", "주행력", isSelected = false),
            Tag("Strength", "소음", isSelected = false),
            Tag("Strength", "효율", isSelected = false),
            Tag("Strength", "파워", isSelected = false),
        )
        val tempImportant = listOf(
            Tag("Important", "디자인", isSelected = false),
            Tag("Important", "차량 보호", isSelected = false),
            Tag("Important", "온도 조절", isSelected = false),
            Tag("Important", "건강", isSelected = false),
            Tag("Important", "신기술", isSelected = false),
            Tag("Important", "안전", isSelected = false)
        )
        val tempUse = listOf(
            Tag("Use", "차박", isSelected = false),
            Tag("Use", "가족여행", isSelected = false)
        )

        _ageList.value = tempAgeList
        _genderList.value = tempGenderList
        _strengthList.value = tempStrength
        _importantList.value = tempImportant
        _useList.value = tempUse
    }

    private fun updateSelectedAge(age: Tag) {
        selectedAge?.isSelected = false
        age.isSelected = true
        selectedAge = age
        _ageList.value = _ageList.value
    }

    private fun updateSelectedGender(gender: Tag) {
        selectedGender?.isSelected = false
        gender.isSelected = true
        selectedGender = gender
        _genderList.value = _genderList.value
    }

    private var cnt = 0
    val isChange = MutableLiveData<Int>(0)

    // ... (기존 코드)

    private fun updateSelectedStrength(strength: Tag) {
        strength.isSelected = !strength.isSelected
        selectedStrength = strength
        if (strength.isSelected) {
            cnt += 1
        } else {
            cnt -= 1
        }
        _strengthList.value = _strengthList.value
        isChange.value = if (cnt == 3) 1 else 0
    }

    private fun updateSelectedImportant(important: Tag) {
        important.isSelected = !important.isSelected
        selectedImportant = important
        if (important.isSelected) {
            cnt += 1
        } else {
            cnt -= 1
        }
        _importantList.value = _importantList.value
        isChange.value = if (cnt == 3) 1 else 0
    }

    private fun updateSelectedUse(use: Tag) {
        use.isSelected = !use.isSelected
        selectedUse = use
        if (use.isSelected) {
            cnt += 1
        } else {
            cnt -= 1
        }
        _useList.value = _useList.value
        isChange.value = if (cnt == 3) 1 else 0
    }

    fun resetSelection() {
        selectedStrength?.isSelected = false
        selectedImportant?.isSelected = false
        selectedUse?.isSelected = false
    }
}
