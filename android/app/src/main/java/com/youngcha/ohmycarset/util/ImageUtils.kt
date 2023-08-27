package com.youngcha.ohmycarset.util


import android.content.Context
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Cache
import okhttp3.OkHttpClient
import java.io.File
import java.util.concurrent.atomic.AtomicInteger
/*
object ImageUtils {
    private lateinit var imageLoader: ImageLoader

    fun initialize(context: Context) {
        val cacheDirectory = File(context.cacheDir, "org_caArt")
        val cacheSize = 50 * 1024 * 1024 // 50 MB

        val okHttpClient = OkHttpClient.Builder()
            .cache(Cache(cacheDirectory, cacheSize.toLong()))
            .build()

        imageLoader = ImageLoader.Builder(context)
            .okHttpClient(okHttpClient)
            .memoryCachePercentage(0.5)
            .diskCacheDirectory(cacheDirectory)
            .diskCacheSizePercentage(0.5)
            .build()
    }

    fun preloadImages(context: Context, urls: List<String>, onImageLoaded: (Int) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val successfulLoads = AtomicInteger(0)

            urls.forEachIndexed { index, url ->
                val request = ImageRequest.Builder(context)
                    .data(url)
                    .build()

                val result = imageLoader.execute(request)
                if (result is SuccessResult) {
                    successfulLoads.incrementAndGet()
                }

                val loadProgress = (successfulLoads.get().toFloat() / urls.size * 100).toInt()

                onImageLoaded(loadProgress)
            }
        }
    }
}

 */