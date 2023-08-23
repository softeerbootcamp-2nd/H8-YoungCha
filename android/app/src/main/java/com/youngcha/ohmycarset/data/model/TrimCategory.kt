package com.youngcha.ohmycarset.data.model

import android.os.Parcelable
import com.youngcha.ohmycarset.enums.TrimType
import kotlinx.parcelize.Parcelize

@Parcelize
data class TrimCategory(
    val type: TrimType,
    val name: String,
    val explain: String,
    var isChecked: Boolean
): Parcelable