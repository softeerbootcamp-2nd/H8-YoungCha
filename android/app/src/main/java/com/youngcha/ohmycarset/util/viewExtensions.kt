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