package com.youngcha.ohmycarset.model.car

data class Car(
    val name: String, // 자동차 이름 ex: 팰리세이드
    val type: String, // 자동차 타입 ex: SUV
    val mainOptions: List<Map<String, List<OptionInfo>>>, // 자동차 옵션 ex: key: "파워트레인" value: {디젤, 가솔린}
    val mainOptionImages: List<Map<String, Int>>, // 자동차 옵션 이미지
    val subOptions: List<Map<String, List<OptionInfo>>>, // 선택 옵션 부분
    val subOptionImage: Map<String, Int>
)