package com.youngcha.ohmycarset.util

import android.view.View

fun View.fadeIn() {
    this.visibility = View.VISIBLE
    this.animate().alpha(1f).setDuration(500).start()
}

fun View.fadeOut() {
    this.animate().alpha(0f).setDuration(500).withEndAction {
        this.visibility = View.GONE
    }.start()
}
fun View.slideInRight() {
    this.visibility = View.VISIBLE // visibility 값을 VISIBLE로 변경
    this.animate().translationX(this.width.toFloat()).setDuration(700).start()
}

fun View.slideOutLeft() {
    this.animate()
        .translationX(-this.width.toFloat())
        .setDuration(700)
        .withEndAction {
            this.visibility = View.GONE
        }
        .start()
}