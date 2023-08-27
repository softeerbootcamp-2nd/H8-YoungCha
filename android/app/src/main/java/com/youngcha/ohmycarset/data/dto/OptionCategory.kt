package com.youngcha.ohmycarset.data.dto

data class OptionCategory(
    val `data`: Data,
    val message: String
) {
    data class Data(
        val categories: List<Category>
    ) {
        data class Category(
            val id: Int,
            val name: String
        )
    }
}