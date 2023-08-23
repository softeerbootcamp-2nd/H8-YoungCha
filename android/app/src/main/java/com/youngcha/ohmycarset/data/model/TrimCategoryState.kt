package com.youngcha.ohmycarset.data.model

data class TrimCategoryState(
    val isFirstLoad: Boolean = false,
    val currTrimCategory: TrimCategory,
    val trimCategories: List<TrimCategory>
)
