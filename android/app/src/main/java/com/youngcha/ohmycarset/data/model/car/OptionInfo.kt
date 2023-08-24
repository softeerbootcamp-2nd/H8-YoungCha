package com.youngcha.ohmycarset.data.model.car

import com.youngcha.ohmycarset.data.dto.Detail
import com.youngcha.ohmycarset.data.dto.Image

data class OptionInfo(
    val id: Int,
    val categoryId: Int,
    val optionType: String, // main, sub, color
    val rate: String, // 63%
    val name: String, // 디젤 2.2
    val price: Int, // + 1,480,000원
    val feedbackTitle: String,
    val feedbackDescription: String,
    val mainImage: String,
    val subImage: String?,
    val logoImage: String?,
    val detail: List<Detail>,
    val guideModeDescription: List<String>? // 가이드 모드 경우 옵션에 대한 세부 정보가 있음 ex) 가이드 모드 파워트레인 부분에 [효율 89%] 이 부분
)
