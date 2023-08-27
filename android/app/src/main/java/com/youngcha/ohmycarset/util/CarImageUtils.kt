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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object CarImageUtils {

    fun load360Images(context: Context, imageUrls: List<String>, onComplete: (List<Drawable>) -> Unit) {
        val img360List = mutableListOf<Drawable>()
        val imageLoader = ImageLoader.Builder(context)
            .memoryCache {
                MemoryCache.Builder(context)
                    .maxSizePercent(0.3)
                    .build()
            }
            .diskCache {
                DiskCache.Builder()
                    .directory(context.cacheDir.resolve("car_360_img"))
                    .maxSizePercent(0.3)
                    .build()
            }
            .build()

        CoroutineScope(Dispatchers.Main).launch {
            imageUrls.forEach { url ->
                val request = ImageRequest.Builder(context)
                    .data(url)
                    .build()
                imageLoader.execute(request).drawable?.let {
                    img360List.add(it)
                }
            }
            onComplete(img360List)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    fun setupImageSwipe(view: ImageView, imgList: List<Drawable>, imageLoader: ImageLoader) {
        var startX = 0f
        view.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    startX = event.x
                    true
                }
                MotionEvent.ACTION_MOVE -> {
                    val dragIdx = (((startX - event.x) / (view.width) * 60).toInt() + 60) % 60
                    val request = ImageRequest.Builder(view.context)
                        .data(imgList[dragIdx])
                        .crossfade(false)
                        .target(view)
                        .placeholder(imgList[dragIdx])
                        .build()
                    try {
                        imageLoader.enqueue(request)
                    } catch (e: NullPointerException) {
                    }
                    true
                }
                else -> false
            }
        }
    }
}
