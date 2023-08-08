package com.youngcha.ohmycarset.model

data class TrimCategoryState(
    val isFirstLoad: Boolean = false,
    val currTrimCategory: TrimCategory,
    val trimCategories: List<TrimCategory>
)
