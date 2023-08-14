package com.youngcha.ohmycarset.ui.customview

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatButton
import androidx.constraintlayout.widget.ConstraintLayout
import com.youngcha.ohmycarset.databinding.LayoutHyundaiButtonBinding
import com.youngcha.ohmycarset.model.car.OptionInfo

class HyundaiButtonView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    var binding: LayoutHyundaiButtonBinding

    init {
        val inflater = LayoutInflater.from(context)

        binding = LayoutHyundaiButtonBinding.inflate(inflater, this, true)

    }

    private var onClickAction: (() -> Unit)? = null

    fun setOnClickAction(action: (() -> Unit)?) {
        onClickAction = action
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_UP) {
            onClickAction?.invoke()
        }
        return super.dispatchTouchEvent(event)
    }


    fun setOptionInfo(optionInfo: OptionInfo) {
        binding.optionInfo = optionInfo
    }

    fun setIsVisible(isVisible: Int) {
        binding.isVisible = isVisible
    }
}