package com.youngcha.ohmycarset.util

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.text.style.ReplacementSpan
import androidx.core.content.ContextCompat
import com.youngcha.ohmycarset.R

class RoundedBackgroundSpan(private val context: Context) : ReplacementSpan() {
    private val bgColor = ContextCompat.getColor(context, R.color.tag_sky_blue)
    private val textColor = ContextCompat.getColor(context, R.color.main_hyundai_blue)
    private val padding = 15
    private val radius = 3

    override fun getSize(paint: Paint, text: CharSequence, start: Int, end: Int, fm: Paint.FontMetricsInt?): Int {
        return (2 * padding + paint.measureText(text, start, end)).toInt()
    }

    @SuppressLint("ResourceAsColor")
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
        val rect = RectF(x, top.toFloat() - padding / 2, x + width + 2 * padding, bottom.toFloat() + padding / 2)

        paint.color = bgColor
        canvas.drawRoundRect(rect, radius.toFloat(), radius.toFloat(), paint)

        paint.color = textColor
        canvas.drawText(text, start, end, x + padding, y.toFloat(), paint)
    }
}

