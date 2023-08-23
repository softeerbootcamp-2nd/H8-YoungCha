package com.youngcha.ohmycarset.data.dto

data class Data(
    val categoryId: Int,
    val details: List<Detail>,
    val feedbackDescription: String,
    val feedbackTitle: String,
    val id: Int,
    val images: List<Image>,
    val name: String,
    val price: Int,
    val rate: Int
)