package com.youngcha.ohmycarset.util.decorator

import android.graphics.Rect
import android.util.DisplayMetrics
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class TopSpacingItemDecoration(private val space: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView, state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        val resources = parent.context.resources
        val pixelValue = (space * resources.displayMetrics.density).toInt()
        if (position != 0) { // 첫 번째 아이템이 아니면
            outRect.top = pixelValue // marginTop을 24dp에 해당하는 픽셀 값으로 설정
        }
    }
}