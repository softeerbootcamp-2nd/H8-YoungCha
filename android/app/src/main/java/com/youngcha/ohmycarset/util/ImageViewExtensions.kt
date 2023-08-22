package com.youngcha.ohmycarset.util

import android.annotation.SuppressLint
import android.view.MotionEvent
import android.widget.ImageView
import androidx.core.widget.NestedScrollView

/*
이미지 뷰에 터치 리스너를 설정하여 사용자가 화면을 좌우로 스와이프할 때마다 이미지를 변경하는 기능을 구현합니다.
사용자가 이미지를 터치하면 처음 터치 위치를 저장하고, 스와이프하는 동안 이 위치를 감지하여 얼마나 많이 이동했는지 계산합니다.
이동한 거리에 따라 다음으로 표시할 이미지의 인덱스를 결정합니다. 만약 사용자가 너무 빠르게 스와이프하여 인덱스가 이미지 리스트의 범위를 벗어나면, 인덱스를 다시 0 또는 마지막으로 설정하여 이미지 리스트를 순환합니다.

사용법
val images = List(60) { id ->
    resources.getIdentifier("image_0${id + 1}", "drawable", requireContext().packageName)
}

binding.fragmentEstimate.ivEstimateDone.setupImageSwipe(images)
 */
@SuppressLint("ClickableViewAccessibility")
fun ImageView.setupImageSwipeWithScrollView(images: List<Int>) {
    var downX = 0f
    var index = 0
    this.setImageResource(images[index])

    this.setOnTouchListener { _, event ->
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                downX = event.x
            }
            MotionEvent.ACTION_MOVE -> {
                val distance = downX - event.x
                downX = event.x
                val moveIndex = (distance / this.width * images.size).toInt()
                index = ((index + moveIndex) % images.size + images.size) % images.size
                this.setImageResource(images[index])
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
            }
        }
        true
    }
}
