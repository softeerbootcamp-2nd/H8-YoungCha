package com.youngcha.ohmycarset.ui.adapter.binding

import android.graphics.Color
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.databinding.BindingAdapter
import com.youngcha.ohmycarset.ui.customview.CircleView

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, imageUrl: String) {
    // 여기에 이미지 로딩 로직 (Glide | Coil)
}

@BindingAdapter("testImageSource")
fun loadImage(view: ImageView, imageUrl: Int) {
    if (imageUrl == 1) return
    view.setImageResource(imageUrl)
}

@BindingAdapter("app:fillColor")
fun setFillColor(view: CircleView, colorStr: String?) {
    if (!colorStr.isNullOrEmpty()) {
        try {
            view.setFillColor(Color.parseColor(colorStr))
        } catch (e: IllegalArgumentException) {
            // 색상 문자열이 잘못된 경우 처리
        }
    }
}

/*
<ImageView
    ...
    app:testImageSource="@{@drawable/img_test1}" />
 */

@BindingAdapter("app:isVisibleForRankZero")
fun View.isVisibleForRankZero(rank: Int) {
    visibility = if (rank == 0) View.VISIBLE else View.GONE
}

@BindingAdapter("dynamicWidth")
fun setDynamicWidth(cardView: CardView, widthDp: Float) {
    val widthPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, widthDp, cardView.resources.displayMetrics)
    val layoutParams = cardView.layoutParams
    layoutParams.width = widthPx.toInt()
    cardView.layoutParams = layoutParams
}

@BindingAdapter("dynamicHeight")
fun setDynamicHeight(cardView: CardView, heightDp: Float) {
    val heightPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, heightDp, cardView.resources.displayMetrics)
    val layoutParams = cardView.layoutParams
    layoutParams.height = heightPx.toInt()
    cardView.layoutParams = layoutParams
}
