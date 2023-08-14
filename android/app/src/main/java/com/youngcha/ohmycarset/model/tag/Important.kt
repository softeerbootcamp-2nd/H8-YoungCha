package com.youngcha.ohmycarset.model.tag

data class Important(val important:String) {
    var isSelected:Boolean =false

    override fun toString(): String {
        return important.toString()
    }
}