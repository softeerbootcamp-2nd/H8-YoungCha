package com.youngcha.ohmycarset.data.dto

data class DataXX(
    val categoryId: Int,
    val checked: Boolean,
    val details: List<DetailX>,
    val feedbackDescription: String,
    val feedbackTitle: String,
    val id: Int,
    val images: List<ImageX>,
    val name: String,
    val price: Int,
    val rate: Int,
    val tags: List<Tag>
)