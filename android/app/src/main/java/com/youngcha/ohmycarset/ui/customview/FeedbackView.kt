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
import com.youngcha.ohmycarset.data.model.car.OptionInfo

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
        binding.ivFace.setImageResource(R.drawable.ic_face)
        binding.clVpSelected.visibility = View.VISIBLE
        binding.ivGood.clearAnimation() // 이전 애니메이션 제거
        binding.ivGood.visibility = View.INVISIBLE
        val fadeInInitial = AnimationUtils.loadAnimation(context, R.anim.fade_in)
        val fadeInForGood = AnimationUtils.loadAnimation(context, R.anim.fade_in)

        // First Animation
        fadeInInitial.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {
                Handler(Looper.getMainLooper()).postDelayed({
                    binding.ivFace.setImageResource(R.drawable.ic_smile)
                    binding.ivGood.startAnimation(fadeInForGood)
                }, 500) // 0.5초 후 실행
            }

            override fun onAnimationRepeat(animation: Animation?) {}
        })

        // Second Animation
        fadeInForGood.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
                binding.ivGood.visibility = View.VISIBLE
            }

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

