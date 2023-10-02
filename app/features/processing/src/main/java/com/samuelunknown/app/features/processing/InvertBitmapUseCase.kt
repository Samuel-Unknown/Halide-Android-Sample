package com.samuelunknown.app.features.processing

import android.graphics.Bitmap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Invert bitmap colors
 */
interface InvertBitmapUseCase {
    suspend fun execute(bitmap: Bitmap): Bitmap
}

class InvertBitmapUseCaseImpl : InvertBitmapUseCase {

    private external fun invertFromJNI(
        input: ByteArray,
        inputWidth: Int,
        inputHeight: Int
    ): ByteArray

    private fun Bitmap.toByteArray(withAlphaChannel: Boolean = false): ByteArray {
        val pixels = IntArray(width * height)
        getPixels(pixels, 0, width, 0, 0, width, height)

        // 4 (ARGB) or 3(RGB) bytes per pixel
        val gridSize = width * height
        val channels = if (withAlphaChannel) 4 else 3
        val imageBytes = ByteArray(gridSize * channels)
        var byteIndex = 0

        for (i in pixels.indices) {
            val pixel = pixels[i]

            if (withAlphaChannel) {
                imageBytes[byteIndex] = (pixel shr 24 and 0xFF).toByte() // A
            }

            imageBytes[byteIndex + if (withAlphaChannel) gridSize else 0] =
                (pixel shr 16 and 0xFF).toByte() // R

            imageBytes[byteIndex + if (withAlphaChannel) gridSize * 2 else gridSize] =
                (pixel shr 8 and 0xFF).toByte() // G

            imageBytes[byteIndex + if (withAlphaChannel) gridSize * 3 else gridSize * 2] =
                (pixel and 0xFF).toByte() // B

            byteIndex += 1
        }

        return imageBytes
    }

    private fun ByteArray.toBitmap(width: Int, height: Int, withAlphaChannel: Boolean = false): Bitmap {
        // Assuming 'width' and 'height' are the dimensions of the processed image
        val resultBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val pixels = IntArray(width * height)
        var byteIndex = 0
        val gridSize = width * height

        for (i in pixels.indices) {
            val a = if (withAlphaChannel) this[byteIndex].toInt() and 0xFF else 0xFF
            val r = this[byteIndex + if (withAlphaChannel) gridSize else 0].toInt() and 0xFF
            val g = this[byteIndex + if (withAlphaChannel) gridSize * 2 else gridSize].toInt() and 0xFF
            val b = this[byteIndex + if (withAlphaChannel) gridSize * 3 else gridSize * 2].toInt() and 0xFF

            pixels[i] = (a shl 24) or (r shl 16) or (g shl 8) or b
            byteIndex += 1
        }

        resultBitmap.setPixels(pixels, 0, width, 0, 0, width, height)
        return resultBitmap
    }

    override suspend fun execute(bitmap: Bitmap): Bitmap = withContext(Dispatchers.Default) {
        val input: ByteArray = bitmap.toByteArray()
        val output: ByteArray = invertFromJNI(input, bitmap.width, bitmap.height)

        return@withContext output.toBitmap(bitmap.width, bitmap.height)
    }

    private companion object {
        // Used to load the 'processing' library on application startup.
        init {
            System.loadLibrary("processing")
        }
    }
}