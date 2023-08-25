package com.youngcha.ohmycarset.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GuideParam(
    val id: Int,
    val gender: String,
    val age: Int,
    val keyword1Id: Int,
    val keyword2Id: Int,
    val keyword3Id: Int
) : Parcelable