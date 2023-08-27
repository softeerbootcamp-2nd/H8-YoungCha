package com.youngcha.ohmycarset.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.youngcha.ohmycarset.data.model.tag.Tag

class UserTagViewModel : ViewModel() {

    private val _tagNumbers = MutableLiveData<List<Int>>()
    val tagNumbers: LiveData<List<Int>> = _tagNumbers

    // 참조 태그 목록
    val referenceTags = listOf(
        "주행력",
        "소음",
        "효율",
        "파워",
        "디자인",
        "차량 보호",
        "온도 조절",
        "건강",
        "신기술",
        "안전",
        "차박",
        "가족 여행"
    )

    val ageGenderReference = mapOf(
        "20대" to 2,
        "30대" to 3,
        "40대" to 4,
        "50대" to 5,
        "60대" to 6,
        "70대 이상" to 7,
        "남성" to 0,
        "여성" to 1,
        "선택 안함" to 2
    )

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

    private val _selectedTag = MutableLiveData<Tag?>()

    private var cnt = 0
    val isChange = MutableLiveData<Int>(0)

    // 선택된 tag
    var selectedList = mutableListOf<Tag>()

    private var selectedAge: Tag? = null
    private var selectedGender: Tag? = null
    private var selectedStrength: Tag? = null
    private var selectedImportant: Tag? = null
    private var selectedUse: Tag? = null

    fun getTagNumber() {
        val numbers = mutableListOf<Int>()

        // ageList에서 isSelected가 true인 태그 번호 추가
        val ageSelected = ageList.value?.filter { it.isSelected }?.mapNotNull {
            ageGenderReference[it.name]
        }

        if (ageSelected.isNullOrEmpty()) {
            numbers.add(2)  // 연령 값이 선택되지 않은 경우 2 추가
        } else {
            numbers.addAll(ageSelected)
        }

        // genderList에서 isSelected가 true인 태그 번호 추가
        val genderSelected = genderList.value?.filter { it.isSelected }?.mapNotNull {
            ageGenderReference[it.name]
        }

        if (genderSelected.isNullOrEmpty()) {
            numbers.add(2)  // 성별 값이 선택되지 않은 경우 2 추가
        } else {
            numbers.addAll(genderSelected)
        }

        // selectedList에서 해당하는 태그 번호 추가
        numbers.addAll(
            selectedList.mapNotNull { tag ->
                val index = referenceTags.indexOf(tag.name)
                if (index != -1) {
                    Log.d("로그", index.toString() + " !@!@")
                    index + 1
                } else {
                    null
                }
            }
        )

        _tagNumbers.value = numbers
    }

    init {
        initData()
    }

    private fun initData() {
        val tempAgeList = listOf(
            Tag("0", "Age", "20대", isSelected = false),
            Tag("0", "Age", "30대", isSelected = false),
            Tag("0", "Age", "40대", isSelected = false),
            Tag("0", "Age", "50대", isSelected = false),
            Tag("0", "Age", "60대", isSelected = false),
            Tag("0", "Age", "70대 이상", isSelected = false)
        )
        val tempGenderList = listOf(
            Tag("0", "Gender", "여성", isSelected = false),
            Tag("0", "Gender", "남성", isSelected = false),
            Tag("0", "Gender", "선택 안함", isSelected = false)
        )
        val tempStrength = listOf(
            Tag("0", "Strength", "주행력", isSelected = false),
            Tag("0", "Strength", "소음", isSelected = false),
            Tag("0", "Strength", "효율", isSelected = false),
            Tag("0", "Strength", "파워", isSelected = false),
        )
        val tempImportant = listOf(
            Tag("0", "Important", "디자인", isSelected = false),
            Tag("0", "Important", "차량 보호", isSelected = false),
            Tag("0", "Important", "온도 조절", isSelected = false),
            Tag("0", "Important", "건강", isSelected = false),
            Tag("0", "Important", "신기술", isSelected = false),
            Tag("0", "Important", "안전", isSelected = false)
        )
        val tempUse = listOf(
            Tag("0", "Use", "차박", isSelected = false),
            Tag("0", "Use", "가족여행", isSelected = false)
        )

        _ageList.value = tempAgeList
        _genderList.value = tempGenderList
        _strengthList.value = tempStrength
        _importantList.value = tempImportant
        _useList.value = tempUse
    }


    fun onTagLongItemClick(tag: Tag) {
        when (tag.tagType) {
            "Age" -> updateSelectedAge(tag)
            "Gender" -> updateSelectedGender(tag)
        }
        _selectedTag.value = tag
    }

    fun onTagShortItemClick(tag: Tag) {
        if (isChange.value == 1 && !tag.isSelected) {
            return
        }
        when (tag.tagType) {
            "Strength" -> updateSelectedStrength(tag)
            "Important" -> updateSelectedImportant(tag)
            "Use" -> updateSelectedUse(tag)
        }
        if (tag.isSelected) {
            selectedList.add(tag)
            tag.tagOrder = (selectedList.indexOf(tag) + 1).toString()
        } else {
            selectedList.remove(tag)
            for (i in selectedList.indices) {
                if (selectedList[i].tagOrder != "1") {
                    selectedList[i].tagOrder = (selectedList[i].tagOrder.toInt() - 1).toString()
                    tag.tagOrder = selectedList[i].tagOrder
                }
            }
        }
        updateTag()
        _selectedTag.value = tag
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

    fun updateTag() {
        _strengthList.value = _strengthList.value
        _importantList.value = _importantList.value
        _useList.value = _useList.value
    }
}
