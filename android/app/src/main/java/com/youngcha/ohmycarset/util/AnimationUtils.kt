package com.youngcha.ohmycarset.util

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.res.Resources
import android.graphics.Color
import android.graphics.Path
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout
import android.widget.TextView
import java.lang.Math.cos
import java.lang.Math.sin
import kotlin.random.Random

object AnimationUtils {

    fun explodeView(frameLayout: FrameLayout, numberOfParticles: Int = 150, numberOfExplosions: Int = 5) {
        val colors = listOf("#FF7676", "#FD3F33", "#FFB876", "#FFB801", "#76ADFF", "#357FED")

        repeat(numberOfExplosions) {
            // 화면 상단의 랜덤 위치에서 폭죽 시작
            val explosionCenterX = Random.nextInt(frameLayout.width).toFloat()
            val explosionCenterY = Random.nextInt(frameLayout.height / 3).toFloat()

            val particles = List(numberOfParticles) {
                val width = Random.nextInt(20) + 10
                val height = Random.nextInt(20) + 8
                val color = Color.parseColor(colors[Random.nextInt(colors.size)])

                val particle = View(frameLayout.context).apply {
                    setBackgroundColor(color)
                    layoutParams = FrameLayout.LayoutParams(width, height)
                    x = explosionCenterX - width / 2f
                    y = explosionCenterY - height / 2f
                    alpha = 0.8f
                }
                frameLayout.addView(particle)
                particle
            }

            particles.forEach { particle ->
                val angle = Math.toRadians(Random.nextInt(360).toDouble())
                val speed = Random.nextInt(10) + 5  // 5~15 사이의 속도

                val xVelocity = cos(angle) * speed
                val yVelocity = sin(angle) * speed

                val animator = ValueAnimator.ofFloat(0f, 1f).apply {
                    duration = (Random.nextInt(3001) + 2000).toLong()  // 2~5초
                    interpolator = LinearInterpolator()
                    addUpdateListener {
                        particle.x += xVelocity.toFloat()
                        particle.y += yVelocity.toFloat()

                        // 화면 밖으로 나가면 삭제
                        if (particle.x < 0 || particle.x > frameLayout.width || particle.y < 0 || particle.y > frameLayout.height) {
                            frameLayout.removeView(particle)
                            this.cancel()
                        }
                    }
                }

                animator.addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        frameLayout.removeView(particle)
                    }
                })

                animator.start()
            }
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