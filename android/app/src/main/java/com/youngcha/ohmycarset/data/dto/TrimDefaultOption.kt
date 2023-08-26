package com.youngcha.ohmycarset.data.dto

data class TrimDefaultOption(
    val `data`: Data,
    val message: String
) {
    data class Data(
        val contents: List<Content>,
        val first: Boolean,
        val last: Boolean,
        val totalElements: Int,
        val totalPages: Int,
        val trimId: Int
    ) {
        data class Content(
            val categoryId: Int,
            val imgUrl: String,
            val name: String
        )
    }
}