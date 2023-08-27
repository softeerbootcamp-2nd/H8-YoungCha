package com.youngcha.ohmycarset.util

import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.widget.TextView

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

fun ViewGroup.findTextViews(): List<TextView> {
    val textViews = mutableListOf<TextView>()
    for (i in 0 until childCount) {
        val child = getChildAt(i)
        if (child is TextView) {
            textViews.add(child)
        } else if (child is ViewGroup) {
            textViews.addAll(child.findTextViews())
        }
    }
    return textViews
}