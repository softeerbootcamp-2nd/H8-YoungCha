package com.youngcha.ohmycarset.ui.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.youngcha.ohmycarset.R

class CircleCustomView@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint().apply {
        isAntiAlias = true
    }

    private var borderColor = Color.TRANSPARENT
    private var borderWidth = 0f

    init {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.CircleCustomView)
            borderColor = typedArray.getColor(R.styleable.CircleCustomView_borderColor, Color.TRANSPARENT)
            borderWidth = typedArray.getDimension(R.styleable.CircleCustomView_borderWidth, 0f)
            paint.color = typedArray.getColor(R.styleable.CircleCustomView_fillColor, Color.BLACK)
            typedArray.recycle()
        }
    }

    override fun onDraw(canvas: Canvas) {
        val radius = (width.coerceAtMost(height) / 2 - borderWidth).coerceAtLeast(0f)
        val cx = width / 2f
        val cy = height / 2f

        if (borderWidth > 0) {
            paint.style = Paint.Style.STROKE
            paint.strokeWidth = borderWidth * 2
            paint.color = borderColor
            canvas.drawCircle(cx, cy, radius, paint)
        }

        paint.style = Paint.Style.FILL
        paint.color = (paint.color and 0x00FFFFFF) or 0xFF000000.toInt() // 배경색
        canvas.drawCircle(cx, cy, radius - borderWidth, paint)
    }

    fun setFillColor(color: Int) {
        paint.color = color
        invalidate()
    }

    fun setBorder(color: Int, width: Float) {
        borderColor = color
        borderWidth = width
        invalidate()
    }
}