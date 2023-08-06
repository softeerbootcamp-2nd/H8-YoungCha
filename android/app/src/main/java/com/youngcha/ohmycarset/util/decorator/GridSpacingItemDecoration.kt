package com.youngcha.ohmycarset.util.decorator

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class GridSpacingItemDecoration(private val spanCount: Int, private val spacing: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView, state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view) // item position
        val column = position % spanCount // item column

        if (position > spanCount - 1) { // 첫 번째 행이 아닐 경우 marginTop을 설정
            outRect.top = 8
        }

        if (column == 0) {
            outRect.right = spacing
        } else {
            outRect.left = spacing
        }
    }
}