package com.youngcha.ohmycarset.util


import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.MotionEvent
import android.widget.ImageView
import coil.ImageLoader
import coil.disk.DiskCache
import coil.memory.MemoryCache
import coil.request.ImageRequest
import coil.request.SuccessResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object CarImageUtils {

    fun load360Images(
        context: Context,
        imageUrls: List<String>,
        onStart: () -> Unit,
        onComplete: (List<Drawable>) -> Unit
    ) {
        val imageLoader = ImageLoader.Builder(context).build()
        val img360List = mutableListOf<Drawable>()

        GlobalScope.launch(Dispatchers.IO) {
            onStart.invoke()  // 시작시 로딩 시작 알림

            for (url in imageUrls) {
                val request = ImageRequest.Builder(context).data(url).build()
                val result = imageLoader.execute(request)
                if (result is SuccessResult) {
                    img360List.add(result.drawable)
                }
            }

            withContext(Dispatchers.Main) {
                onComplete.invoke(img360List)  // 모든 이미지 로딩 후 콜백 실행
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    fun setupImageSwipe(
        imageView: ImageView,
        images: List<Drawable>,
        imageLoader: ImageLoader
    ) {
        var startX = 0f

        imageView.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    startX = event.x
                    true
                }
                MotionEvent.ACTION_MOVE -> {
                    val dragIdx = (((startX - event.x) / imageView.width * 60).toInt() + 60) % 60
                    val request = ImageRequest.Builder(imageView.context)
                        .data(images[dragIdx])
                        .crossfade(false)
                        .target(imageView)
                        .placeholder(images[dragIdx])
                        .build()

                    try {
                        imageLoader.enqueue(request)
                    } catch (e: NullPointerException) {}

                    true
                }
                else -> false
            }
        }
    }
}