package com.youngcha.ohmycarset.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.youngcha.ohmycarset.R
import com.youngcha.ohmycarset.model.TrimSelfMode
import com.youngcha.ohmycarset.model.TrimSelfModeExteriorColor
import com.youngcha.ohmycarset.model.TrimSelfModeInternalColor
import com.youngcha.ohmycarset.model.TrimSelfModeMainOption

class TrimSelfModeViewModel: ViewModel() {
    private val _trimSelfModeData = MutableLiveData<TrimSelfMode>()
    val trimSelfModeData: LiveData<TrimSelfMode> = _trimSelfModeData

    init {
        loadTrimSelfMode()
    }

    private fun loadTrimSelfMode() {
        // 임시 데이터 생성
        val tempTrimSelfMode = TrimSelfMode(
            id = 1L,
            name = "펠리세이드",
            imgUrl = R.drawable.img_leblanc,
            hashtag = "#Example1",
            description = "Le Blanc 르블랑",
            isBest = true,
            minPrice = "38,960,000원 부터",
            mainOptions = listOf(
                TrimSelfModeMainOption(
                    imgUrl = R.drawable.img_core_option_explain,
                    description = "Main option 1"
                )
            ),
            exteriorColor = listOf(
                TrimSelfModeExteriorColor(code = "#FAFAFA", name = "크리미 화이트 펄"),
                TrimSelfModeExteriorColor(code = "#111111", name = "어비스 블랙펄"),
                TrimSelfModeExteriorColor(code = "#9B9D9C", name = "쉬머링 실버 메탈릭"),
                TrimSelfModeExteriorColor(code = "#2C2925", name = "그라파이트 그레이 메탈"),
                TrimSelfModeExteriorColor(code = "#1C2234", name = "문라이트 블루 펄"),
                TrimSelfModeExteriorColor(code = "#333635", name = "가이아 브라운 펄")
            ),
            internalColor = listOf(
                TrimSelfModeInternalColor(url = R.drawable.img_quilting_natural, name = "퀄팅 천연(블랙)"),
                TrimSelfModeInternalColor(url = R.drawable.img_cool_grey, name = "쿨 그레이")
            )
        )

        _trimSelfModeData.value = tempTrimSelfMode
    }
}