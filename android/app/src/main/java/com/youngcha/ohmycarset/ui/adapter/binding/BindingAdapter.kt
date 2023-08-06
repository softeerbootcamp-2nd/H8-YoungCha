package com.youngcha.ohmycarset.ui.adapter.binding

import android.graphics.Color
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.youngcha.ohmycarset.ui.customview.CircleCustomView

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, imageUrl: String) {
    // 여기에 이미지 로딩 로직 (Glide | Coil)
}

@BindingAdapter("testImageSource")
fun loadImage(view: ImageView, imageUrl: Int) {
    view.setImageResource(imageUrl)
}

@BindingAdapter("app:fillColor")
fun setFillColor(view: CircleCustomView, colorStr: String?) {
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