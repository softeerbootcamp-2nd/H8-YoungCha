package com.youngcha.ohmycarset.data.model.car

//data class Car(
//    val name: String, // 자동차 이름 ex: 팰리세이드
//    val type: String, // 자동차 타입 ex: SUV
//    val mainOptions: List<Map<String, List<OptionInfo>>>, // 자동차 옵션 ex: key: "파워트레인" value: {디젤, 가솔린}
//  //  val mainOptionImages: List<Map<String, Int>>, // 자동차 옵션 이미지
//    val subOptions: List<Map<String, List<OptionInfo>>>// 선택 옵션 부분
//   // val subOptionImage: Map<String, Int>
//)

data class Car(
    val name: String,
    val type: String,
    val mainOptions: List<Map<String, List<OptionInfo>>>,
    val subOptions: List<Map<String, List<OptionInfo>>>
) {
    override fun toString(): String {
        val mainOptionsString = mainOptions.joinToString("\n") { entry ->
            val (key, value) = entry.entries.first()  // Map에 한 개의 엔트리만 있을 것으로 가정
            "$key: ${value.joinToString(", ") { it.name }}"
        }

        val subOptionsString = subOptions.joinToString("\n") { entry ->
            val (key, value) = entry.entries.first()  // Map에 한 개의 엔트리만 있을 것으로 가정
            "$key: ${value.joinToString(", ") { it.name }}"
        }

        return "Car name: $name\nType: $type\nMain Options:\n$mainOptionsString\nSub Options:\n$subOptionsString"
    }
}
