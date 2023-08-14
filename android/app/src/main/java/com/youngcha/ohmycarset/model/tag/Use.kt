package com.youngcha.ohmycarset.model.tag

data class Use(val use:String) {
    var isSelected:Boolean =false

    override fun toString(): String {
        return use.toString()
    }
}