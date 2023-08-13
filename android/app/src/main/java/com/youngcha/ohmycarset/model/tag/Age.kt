package com.youngcha.ohmycarset.model.tag

data class Age(val age:String) {
    var isSelected:Boolean =false

    override fun toString(): String {
        return age.toString()
    }
}