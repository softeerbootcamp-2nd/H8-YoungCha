package com.youngcha.ohmycarset.model.tag

data class Gender(val age:String) {
    var isSelected:Boolean =false

    override fun toString(): String {
        return age.toString()
    }
}