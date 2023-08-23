package com.youngcha.ohmycarset.util

import android.annotation.SuppressLint
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ImageSpan
import android.text.style.ReplacementSpan
import android.text.style.StyleSpan
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.TextView

@SuppressLint("ClickableViewAccessibility")
fun TextView.baekcasajeon2(
    options: Map<String, KeywordOptions>,
    onClick: (keyword: String) -> Unit
) {
    val originalFullText = this.text.toString()
    val spannable = SpannableStringBuilder(originalFullText)
    for ((keyword, option) in options) {
        var start = originalFullText.indexOf(keyword)
        while (start != -1) {
            val adjustedStart = start
            val adjustedEnd = adjustedStart + keyword.length

            val padding = Rect(option.paddingLeft, option.paddingTop, option.paddingRight, option.paddingBottom)
            val backgroundSpan = RoundedBackgroundSpan2(option.textColor, option.backgroundColor, padding, option.cornerRadius)
            spannable.setSpan(backgroundSpan, adjustedStart, adjustedEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

            val styleSpan = StyleSpan(option.textStyle)
            spannable.setSpan(styleSpan, adjustedStart, adjustedEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

            val clickableSpan = object : ClickableSpan() {
                override fun onClick(widget: View) {
                    onClick(keyword)
                }

                override fun updateDrawState(ds: TextPaint) {
                    super.updateDrawState(ds)
                    ds.isUnderlineText = false
                    ds.bgColor = Color.TRANSPARENT
                    ds.color = option.textColor
                    ds.linkColor = Color.TRANSPARENT  // 클릭 시 링크 배경색을 투명으로 설정
                }
            }
            spannable.setSpan(clickableSpan, adjustedStart, adjustedEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

            start = originalFullText.indexOf(keyword, start + keyword.length)  // 원본 텍스트에서 다음 키워드 위치를 찾습니다.
        }
    }

    this.text = spannable
    this.movementMethod = LinkMovementMethod.getInstance()

    this.setOnTouchListener { v, event ->
        if (event.action == MotionEvent.ACTION_DOWN || event.action == MotionEvent.ACTION_UP) {
            v.isPressed = false
            v.invalidate()
        }
        v.performClick()
        return@setOnTouchListener false
    }

}



data class KeywordOptions(
    val textColor: Int,
    val textStyle: Int = Typeface.NORMAL,
    val backgroundColor: Int,
    val iconRes: Int? = null,
    val paddingLeft: Int = 0,
    val paddingTop: Int = 0,
    val paddingRight: Int = 0,
    val paddingBottom: Int = 0,
    val cornerRadius: Float = 0f
)

class RoundedBackgroundSpan2(
    private val textColor: Int,
    private val backgroundColor: Int,
    private val padding: Rect,
    private val cornerRadius: Float
) : ReplacementSpan() {
    override fun getSize(
        paint: Paint,
        text: CharSequence?,
        start: Int,
        end: Int,
        fm: Paint.FontMetricsInt?
    ): Int {
        return paint.measureText(text, start, end).toInt() + padding.left + padding.right
    }

    override fun draw(
        canvas: Canvas,
        text: CharSequence,
        start: Int,
        end: Int,
        x: Float,
        top: Int,
        y: Int,
        bottom: Int,
        paint: Paint
    ) {
        val width = paint.measureText(text, start, end)
        val rect = RectF(
            x + padding.left,  // 패딩 추가
            top.toFloat() + padding.top,
            x + width + padding.right,  // 여기에 패딩 추가
            bottom.toFloat() - padding.bottom
        )

        paint.color = backgroundColor
        canvas.drawRoundRect(rect, cornerRadius, cornerRadius, paint)

        paint.color = textColor
        canvas.drawText(text, start, end, x + padding.left, y.toFloat(), paint)  // 패딩을 추가한 x 위치에서 텍스트를 그립니다.
    }
}