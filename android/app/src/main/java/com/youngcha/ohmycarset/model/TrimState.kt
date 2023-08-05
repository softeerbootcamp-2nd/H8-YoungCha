package com.youngcha.ohmycarset.model

data class TrimState(
    val isFirstLoad: Boolean = false,
    val currTrim: Trim,
    val trims: List<Trim>
)
