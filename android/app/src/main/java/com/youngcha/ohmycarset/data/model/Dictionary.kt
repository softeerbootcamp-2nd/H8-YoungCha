package com.youngcha.ohmycarset.data.model

data class Dictionary(
    val `data`: List<Data>,
    val message: String
) {
    data class Data(
        val description: String,
        val imgUrl: String,
        val word: String
    )
}