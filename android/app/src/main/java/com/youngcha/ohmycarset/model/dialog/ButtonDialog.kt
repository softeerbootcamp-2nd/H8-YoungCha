package com.youngcha.ohmycarset.model.dialog

data class ButtonDialog(
    val type: String,
    val icon: Int,
    val title: String,
    val buttonHorizontal: ButtonHorizontal,
    val buttonVertical: ButtonVertical
) {
}