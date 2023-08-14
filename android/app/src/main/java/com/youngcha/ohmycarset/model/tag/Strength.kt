package com.youngcha.ohmycarset.model.tag

data class Strength(val strength:String) {
    var isSelected:Boolean =false

    override fun toString(): String {
        return strength.toString()
    }
}