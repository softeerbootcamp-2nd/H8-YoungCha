package com.youngcha.ohmycarset.util

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.res.Resources
import android.graphics.Color
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.FrameLayout
import android.widget.TextView
import com.youngcha.ohmycarset.R
import kotlin.random.Random

object AnimationUtils {

    fun explodeView(frameLayout: FrameLayout, numberOfParticles: Int = 500) {
        // Random객체를 초기화
        val random = Random

        //프레임 레이아웃의 너비와 높이를 가져옴
        val parentHeight = frameLayout.height
        val parentWidth = frameLayout.width

        //파티클이 올라갈 최소 높이 설정 (화면의 20%)
        val minUpDistance = parentHeight * 0.2

        //파티클을 생성.파티클은 랜덤한 색상과 크기를 가진 사각형 뷰
        val particles = List(numberOfParticles) {
            val width = random.nextInt(20) + 10//너비는 10 ~ 30사이로 설정
            val height = random.nextInt(20) + 8//높이는 8 ~ 28사이로 설정
            val particle = View(frameLayout.context).apply {
                setBackgroundColor(
                    Color.argb(
                        255,
                        random.nextInt(256),
                        random.nextInt(256),
                        random.nextInt(256)
                    )
                )//랜덤 색상
                layoutParams = FrameLayout.LayoutParams(width, height)
                x = ((parentWidth * 0.5).toFloat() - width * 0.5).toFloat()//시작 위치의 X좌표
                y =
                    ((parentHeight * 0.3).toFloat() - height * 0.5).toFloat()//시작 위치의 Y좌표를 화면의 30%위치로 설정
                alpha = 0.8f
            }
            frameLayout.addView(particle)
            particle
        }

        //각 파티클에 대한 애니메이션 설정
        particles.forEach { particle ->
            val totalDuration = (random.nextInt(2001) + 6000).toLong()//애니메이션의 총 시간은 3000 ~ 5000ms
            val risingDuration = (totalDuration * 0.20).toLong()//올라가는 시간은 총 시간의 50%
            val fallingDuration = totalDuration - risingDuration//나머지 시간 동안 내려옴

            //올라갈 때의 종착지 X, Y좌표 설정
            val endXUp = particle.x + (random.nextInt(2 * parentWidth) - parentWidth).toFloat()
            val endYUp = particle.y - (random.nextInt(parentHeight / 2) + minUpDistance).toFloat()

            //내려올 때의 종착지 X, Y좌표 설정
            val endXDown = endXUp + (random.nextInt(parentWidth) - parentWidth * 0.5).toFloat()
            val endYDown = parentHeight.toFloat()

            // X, Y좌표에 대한 올라가는 애니메이션 설정
            val xAnimatorUp = ObjectAnimator.ofFloat(particle, "x", particle.x, endXUp).apply {
                duration = risingDuration
                interpolator = AccelerateInterpolator()
            }
            val yAnimatorUp = ObjectAnimator.ofFloat(particle, "y", particle.y, endYUp).apply {
                duration = risingDuration
                interpolator = AccelerateInterpolator()
            }

            // X, Y좌표에 대한 내려오는 애니메이션 설정
            val xAnimatorDown = ObjectAnimator.ofFloat(particle, "x", endXUp, endXDown).apply {
                duration = fallingDuration
                interpolator = DecelerateInterpolator()
            }
            val yAnimatorDown = ObjectAnimator.ofFloat(particle, "y", endYUp, endYDown).apply {
                duration = fallingDuration
                interpolator = DecelerateInterpolator()
            }

            //두 애니메이션 세트를 순차적으로 재생
            val animatorSet = AnimatorSet()
            animatorSet.playSequentially(
                AnimatorSet().apply {
                    playTogether(xAnimatorUp, yAnimatorUp)
                },
                AnimatorSet().apply {
                    playTogether(xAnimatorDown, yAnimatorDown)
                }
            )

            //애니메이션 종료 후 파티클 제거
            animatorSet.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    frameLayout.removeView(particle)
                }
            })

            //애니메이션 시작
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
        valueAnimator.duration = 2500L
        valueAnimator.interpolator = DecelerateInterpolator(1.5f)

        valueAnimator.addUpdateListener { animator ->
            val newValue = animator.animatedValue as Int
            textView.text = formatToCurrency(newValue)

            if (endValue < startValue) { // 현재 값이 이전 값보다 작은 경우
                textView.setTextColor(resources.getColor(R.color.main_hyundai_blue, null))
            } else if (endValue > startValue) { // 현재 값이 이전 값보다 큰 경우
                textView.setTextColor(Color.RED)
            }
            // 만약 두 값이 동일하다면, 아무런 색상 변경 없이 진행됩니다.
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