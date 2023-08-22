package com.youngcha.ohmycarset.util

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.res.Resources
import android.graphics.Color
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.FrameLayout
import android.widget.TextView
import kotlin.random.Random

object AnimationUtils {

    fun animateValueChange(
        textView: TextView,
        previousValue: Int,
        currentValue: Int,
        resources: Resources
    ): ValueAnimator {
        val startValue = previousValue
        val endValue = currentValue
        val valueAnimator = ValueAnimator.ofInt(startValue, endValue)
        valueAnimator.duration = 1000L
        valueAnimator.interpolator = DecelerateInterpolator(1.5f)

        valueAnimator.addUpdateListener { animator ->
            val newValue = animator.animatedValue as Int
            textView.text = formatToCurrency(newValue)
        }

        valueAnimator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                textView.setTextColor(resources.getColor(android.R.color.black, null))
            }
        })

        return valueAnimator
    }

    private fun formatToCurrency(value: Int): String {
        return "%,d원".format(value)
    }
}