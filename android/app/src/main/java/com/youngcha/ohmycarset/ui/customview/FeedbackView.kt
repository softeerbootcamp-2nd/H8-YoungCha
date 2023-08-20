package com.youngcha.ohmycarset.ui.customview

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContentProviderCompat.requireContext
import com.youngcha.ohmycarset.R
import com.youngcha.ohmycarset.databinding.LayoutFeedBackBinding
import com.youngcha.ohmycarset.model.car.OptionInfo

class FeedbackView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding: LayoutFeedBackBinding = LayoutFeedBackBinding.inflate(
        LayoutInflater.from(context), this, true)

    var onAnimationEndListener: (() -> Unit)? = null

    fun setMainFeedbackText(text: String) {
        binding.tvMainFeedback.text = text
    }

    fun setSubFeedbackText(text: String) {
        binding.tvSubFeedback.text = text
    }

    fun startFeedbackAnimation() {
        binding.ivGood.visibility = View.INVISIBLE
        binding.ivFace.setImageResource(R.drawable.ic_face)
        binding.clVpSelected.visibility = View.VISIBLE

        val fadeInInitial = AnimationUtils.loadAnimation(context, R.anim.fade_in)
        val fadeOutForFace = AnimationUtils.loadAnimation(context, R.anim.fade_out)
        val fadeInForFace = AnimationUtils.loadAnimation(context, R.anim.fade_in)
        val fadeInForGood = AnimationUtils.loadAnimation(context, R.anim.fade_in)

        // First Animation
        fadeInInitial.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {
                binding.ivFace.startAnimation(fadeOutForFace)
            }

            override fun onAnimationRepeat(animation: Animation?) {}
        })

        // Second Animation
        fadeOutForFace.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {
                binding.ivFace.setImageResource(R.drawable.ic_smile)
                binding.ivFace.startAnimation(fadeInForFace)
            }

            override fun onAnimationRepeat(animation: Animation?) {}
        })

        // Third Animation
        fadeInForFace.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {
                binding.ivGood.visibility = View.VISIBLE
                binding.ivGood.startAnimation(fadeInForGood)
            }

            override fun onAnimationRepeat(animation: Animation?) {}
        })

        // Final Animation
        fadeInForGood.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {
                Handler(Looper.getMainLooper()).postDelayed({
                    onAnimationEndListener?.invoke()
                }, 1000)
            }

            override fun onAnimationRepeat(animation: Animation?) {}
        })

        binding.clVpSelected.startAnimation(fadeInInitial)
    }

}
