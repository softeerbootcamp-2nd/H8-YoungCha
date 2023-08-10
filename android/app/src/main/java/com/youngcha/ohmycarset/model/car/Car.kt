package com.youngcha.ohmycarset.model.car

import com.youngcha.ohmycarset.enums.CarOption
import com.youngcha.ohmycarset.enums.OptionSelection

data class Car(
    val name: String, // 자동차 이름 ex: 팰리세이드
    val type: String, // 자동차 타입 ex: SUV
    val mainOptions: List<Map<CarOption, List<OptionInfo>>>, // 자동차 옵션 ex: key: 파워트레인 value: {디젤, 가솔린}
    val mainOptionImages: List<Map<CarOption, Int>>, // 자동차 옵션 이미지
    val subOptions: List<Map<OptionSelection, List<OptionInfo>>>, // 선택 옵션 부분
    val subOptionImage: Map<CarOption, Int>
)
