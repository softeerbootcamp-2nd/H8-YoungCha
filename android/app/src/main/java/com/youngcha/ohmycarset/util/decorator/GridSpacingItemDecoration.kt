package com.youngcha.ohmycarset.util.decorator

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class GridSpacingItemDecoration(private val spanCount: Int, private val spacingDp: Int, private val topSpacingDp: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView, state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        val column = position % spanCount

        val spacing = dpToPx(spacingDp / 2, view)
        val topSpacing = dpToPx(topSpacingDp, view)

        // 첫 번째 행이 아닌 경우 marginTop을 설정
        if (position >= spanCount) {
            outRect.top = topSpacing
        }

        if (column == 0) {
            outRect.right = spacing
        } else {
            outRect.left = spacing
        }
    }

    private fun dpToPx(dp: Int, view: View): Int {
        return (dp * view.resources.displayMetrics.density).toInt()
    }
}