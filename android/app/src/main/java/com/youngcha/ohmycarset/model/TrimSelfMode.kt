package com.youngcha.ohmycarset.model

// 실제 API -> imgUrl: String
// Test -> imgUrl: Int [drawble에 있는 img로 테스트하기 위해]
data class TrimSelfMode(
    val id: Long,
    val name: String,
    val imgUrl: Int,
    val hashtag: String,
    val description: String,
    val isBest: Boolean,
    val minPrice: String,
    val mainOptions: List<TrimSelfModeMainOption>,
    val exteriorColor: List<TrimSelfModeExteriorColor>,
    val interiorColor: List<TrimSelfModeInteriorColor>,
    val options: List<TrimSelfModeOption>
)
