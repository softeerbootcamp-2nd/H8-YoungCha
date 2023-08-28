package com.youngcha.ohmycarset.data.model.car

data class Car(
    val name: String = "팰리세이드",
    val type: String = "SUV",
    val mainOptions: List<Map<String, List<OptionInfo>>> = emptyList(),
    val subOptions: List<Map<String, List<OptionInfo>>> = emptyList()
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
