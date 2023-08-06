package com.youngcha.ohmycarset.ui.listener

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.widget.NestedScrollView

class CustomScrollChangeListener(
    private val dropDownImage: ImageView,
    private val detailInfoText: TextView
) : NestedScrollView.OnScrollChangeListener {

    override fun onScrollChange(v: NestedScrollView, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int) {
        val isNearBottom = scrollY >= (v.getChildAt(0).height - v.height - 200 * v.resources.displayMetrics.density)

        // 아래로 스크롤될 경우
        if (scrollY > oldScrollY) {
            fadeOutView(dropDownImage)
            fadeOutView(detailInfoText)
        }
        // 위로 스크롤될 경우
        else if (scrollY < oldScrollY && !isNearBottom) {
            fadeInView(dropDownImage)
            fadeInView(detailInfoText)
        }
    }

    private fun fadeOutView(view: View) {
        val fadeOut = ObjectAnimator.ofFloat(view, "alpha", 1f, 0f)
        fadeOut.duration = 300
        fadeOut.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                view.visibility = View.GONE
            }
        })
        fadeOut.start()
    }

    private fun fadeInView(view: View) {
        view.alpha = 0f
        view.visibility = View.VISIBLE
        val fadeIn = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f)
        fadeIn.duration = 300
        fadeIn.start()
    }
}