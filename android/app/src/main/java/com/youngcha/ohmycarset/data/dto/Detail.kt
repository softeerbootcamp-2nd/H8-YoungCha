package com.youngcha.ohmycarset.data.dto

data class Detail(
    val description: String,
    val imgUrl: String,
    val name: String,
    val specs: List<Spec>
)