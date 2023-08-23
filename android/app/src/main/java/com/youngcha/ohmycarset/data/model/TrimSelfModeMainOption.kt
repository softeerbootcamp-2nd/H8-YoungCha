package com.youngcha.ohmycarset.data.model

// 실제 API: imgUrl: String
// Test: imgUrl: Int -> R.drawable/파일 사용위해
data class TrimSelfModeMainOption(
    val imgUrl: Int,
    val description: String
)
