package com.youngcha.ohmycarset.data.model.tag

data class Tag (
    var tagOrder:String,
    val tagType:String, //age, gender, keyword(strength,importance, uses)
    val name:String,
    var isSelected:Boolean
){
}