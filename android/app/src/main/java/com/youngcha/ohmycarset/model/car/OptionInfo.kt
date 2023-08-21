package com.youngcha.ohmycarset.model.car

data class OptionInfo(
    val optionType: String, // main, sub, color
    val rate: String, // 63%
    val name: String, // 디젤 2.2
    val price: String, // + 1,480,000원
    val image: ImageInfo?, // Image가 있는 경우가 있음
    val guideModeDescription: List<String> // 가이드 모드 경우 옵션에 대한 세부 정보가 있음 ex) 가이드 모드 파워트레인 부분에 [효율 89%] 이 부분
)
