package com.youngcha.ohmycarset.ui.customview

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.youngcha.ohmycarset.R

class HyundaiMainColorButtonView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatButton(context, attrs, defStyleAttr) {

    init {
        // 기본 배경 설정
        background = ContextCompat.getDrawable(context, R.drawable.custom_confirmation_button_background)

        gravity = Gravity.CENTER
    }
}