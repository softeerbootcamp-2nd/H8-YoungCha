package com.youngcha.ohmycarset.util

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.MotionEvent
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener

object ImagePreloadHelper {

    fun preloadImagesWithGlide(context: Context, images: List<String>, onCompleted: () -> Unit) {
        var loadedCount = 0

        for (imageUrl in images) {
            Glide.with(context)
                .load(imageUrl)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: com.bumptech.glide.request.target.Target<Drawable>,
                        isFirstResource: Boolean
                    ): Boolean {
                        loadedCount++
                        if (loadedCount == images.size) {
                            onCompleted()
                        }
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable,
                        model: Any,
                        target: com.bumptech.glide.request.target.Target<Drawable>?,
                        dataSource: DataSource,
                        isFirstResource: Boolean
                    ): Boolean {
                        loadedCount++
                        if (loadedCount == images.size) {
                            onCompleted()
                        }
                        return false
                    }
                })
                .preload()
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    fun ImageView.setupImageSwipeWithPreloadedGlide(images: List<String>) {
        var downX = 0f
        var index = 0

        Glide.with(this)
            .load(images[index])
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .skipMemoryCache(false)
            .into(this)

        this.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    downX = event.x
                }
                MotionEvent.ACTION_MOVE -> {
                    val distance = downX - event.x
                    downX = event.x
                    val moveIndex = (distance / this.width * images.size).toInt()
                    index = ((index + moveIndex) % images.size + images.size) % images.size
                    Glide.with(this)
                        .load(images[index])
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .skipMemoryCache(false)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(this)
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {}
            }
            true
        }
    }

}
