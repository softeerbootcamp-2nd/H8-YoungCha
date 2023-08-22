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
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout
import android.widget.TextView
import kotlin.random.Random

object AnimationUtils {
    fun explodeView(frameLayout: FrameLayout, numberOfParticles: Int = 150) {
        val parentHeight = frameLayout.height
        val parentWidth = frameLayout.width
        val endY = parentHeight * 0.7f

        val colors = listOf("#FF7676", "#FD3F33", "#FFB876", "#FFB801", "#76ADFF", "#357FED")

        val particles = List(numberOfParticles) {
            val width = Random.nextInt(20) + 10
            val height = Random.nextInt(20) + 8
            val color = Color.parseColor(colors[Random.nextInt(colors.size)])

            val particle = View(frameLayout.context).apply {
                setBackgroundColor(color)
                layoutParams = FrameLayout.LayoutParams(width, height)
                x = Random.nextInt(parentWidth - width).toFloat()
                y = 0f - height - Random.nextInt(300).toFloat()
                alpha = 0.8f
            }
            frameLayout.addView(particle)
            particle
        }

        particles.forEach { particle ->
            val totalDuration = (Random.nextInt(7001) + 3000).toLong()  // 속도 차이를 늘리기 위해 범위 조절

            // Y좌표에 대한 애니메이션
            val yAnimator = ObjectAnimator.ofFloat(particle, "y", particle.y, endY).apply {
                duration = totalDuration
                interpolator = DecelerateInterpolator()
            }

            // X좌표에 대한 랜덤 이동
            val xRandomizer = ValueAnimator.ofFloat(0f, 1f).apply {
                duration = totalDuration
                addUpdateListener {
                    val randomXOffset = (Random.nextInt(5) - 2).toFloat()
                    particle.x = (particle.x + randomXOffset).coerceIn(0f,
                        (parentWidth - particle.width).toFloat()
                    )
                }
            }

            // 회전 애니메이션
            val rotationAnimator = ObjectAnimator.ofFloat(particle, "rotation", 0f, 360f * (Random.nextInt(3) + 1)).apply {
                duration = totalDuration
                repeatCount = ValueAnimator.INFINITE
                interpolator = LinearInterpolator()
            }

            // Fade out 애니메이션 조절
            val fadeOutAnimator = ObjectAnimator.ofFloat(particle, "alpha", 0.8f, 0f).apply {
                duration = 500
                startDelay = (totalDuration * 0.7).toLong()
            }

            val animatorSet = AnimatorSet().apply {
                playTogether(yAnimator, xRandomizer, fadeOutAnimator, rotationAnimator)
                startDelay = Random.nextInt(1500).toLong()  // 시작 시점에 랜덤한 지연 추가
            }

            animatorSet.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    frameLayout.removeView(particle)
                }
            })

            animatorSet.start()
        }
    }


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