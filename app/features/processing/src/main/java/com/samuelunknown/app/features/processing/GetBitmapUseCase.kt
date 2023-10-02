package com.samuelunknown.app.features.processing

import android.content.Context
import android.graphics.Bitmap
import androidx.core.graphics.drawable.toBitmap
import coil.imageLoader
import coil.request.ErrorResult
import coil.request.ImageRequest
import coil.request.SuccessResult
import coil.size.Size

/**
 * Returns bitmap of file provided by asset string
 */
interface GetBitmapUseCase {
    suspend fun execute(asset: String): Bitmap
}

class GetBitmapUseCaseImpl(
    private val appContext: Context
) : GetBitmapUseCase {
    override suspend fun execute(asset: String): Bitmap {
        val request = ImageRequest.Builder(appContext)
            .data(asset)
            .size(Size.ORIGINAL)
            .allowHardware(false)
            .build()

        return when (val result = appContext.imageLoader.execute(request)) {
            is ErrorResult -> throw result.throwable
            is SuccessResult -> result.drawable.toBitmap()
        }
    }
}