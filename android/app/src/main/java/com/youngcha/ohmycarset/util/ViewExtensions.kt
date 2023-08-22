package com.youngcha.ohmycarset.util

import android.view.View
import android.view.animation.AlphaAnimation

fun View.fadeIn() {
    this.visibility = View.VISIBLE
    val fadeIn = AlphaAnimation(0f, 1f)
    fadeIn.duration = 1000
    this.startAnimation(fadeIn)
}

fun View.fadeOut() {
    val fadeOut = AlphaAnimation(1f, 0f)
    fadeOut.duration = 200
    this.startAnimation(fadeOut)
    this.visibility = View.GONE
}