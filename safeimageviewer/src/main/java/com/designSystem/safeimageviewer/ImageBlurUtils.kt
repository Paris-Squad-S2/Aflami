package com.designSystem.safeimageviewer

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import android.util.Log
import androidx.core.graphics.createBitmap

object ImageBlurUtils {
    private const val TAG = "ImageBlurUtils"
    private const val DEFAULT_BLUR_RADIUS = 15f
    private const val MAX_BLUR_RADIUS = 25f


    fun blurBitmap(bitmap: Bitmap, blurRadius: Float = DEFAULT_BLUR_RADIUS): Bitmap? {
        return try {
            val workingBitmap = if (bitmap.config == Bitmap.Config.HARDWARE) {
                bitmap.copy(Bitmap.Config.ARGB_8888, false)
            } else {
                bitmap
            }

            val radius = blurRadius.coerceIn(1f, MAX_BLUR_RADIUS)

            val config = workingBitmap.config ?: Bitmap.Config.ARGB_8888
            val blurredBitmap = workingBitmap.copy(config, true)

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
                return blurBitmapFallback(blurredBitmap, radius.toInt())
            }

            blurWithRenderScript(blurredBitmap, radius)
        } catch (e: Exception) {
            Log.e(TAG, "Error blurring bitmap: ${e.message}")
            try {

                val workingBitmap = if (bitmap.config == Bitmap.Config.HARDWARE) {
                    bitmap.copy(Bitmap.Config.ARGB_8888, false)
                } else {
                    bitmap
                }
                blurBitmapFallback(workingBitmap, blurRadius.toInt())
            } catch (fallbackException: Exception) {
                Log.e(TAG, "Error in fallback blur method: ${fallbackException.message}")
                null
            }
        }
    }

    @Suppress("DEPRECATION")
    private fun blurWithRenderScript(bitmap: Bitmap, radius: Float): Bitmap {
        val renderScript = RenderScript.create(null)
        val input = Allocation.createFromBitmap(renderScript, bitmap)
        val output = Allocation.createTyped(renderScript, input.type)

        val script = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript))
        script.setRadius(radius)
        script.setInput(input)
        script.forEach(output)
        output.copyTo(bitmap)

        renderScript.destroy()
        return bitmap
    }

    private fun blurBitmapFallback(bitmap: Bitmap, radius: Int): Bitmap {
        val workingBitmap = if (!bitmap.isMutable) {
            bitmap.copy(Bitmap.Config.ARGB_8888, true)
        } else {
            bitmap
        }

        val width = workingBitmap.width
        val height = workingBitmap.height
        val config = workingBitmap.config ?: Bitmap.Config.ARGB_8888
        val blurredBitmap = createBitmap(width, height, config)

        val canvas = Canvas(blurredBitmap)
        val paint = Paint().apply {
            flags = Paint.ANTI_ALIAS_FLAG
        }

        for (i in 0 until radius) {
            canvas.drawBitmap(workingBitmap, -i.toFloat(), 0f, paint)
            canvas.drawBitmap(workingBitmap, i.toFloat(), 0f, paint)
            canvas.drawBitmap(workingBitmap, 0f, -i.toFloat(), paint)
            canvas.drawBitmap(workingBitmap, 0f, i.toFloat(), paint)
        }

        return blurredBitmap
    }


    fun createNSFWBlur(bitmap: Bitmap): Bitmap? {
        return blurBitmap(bitmap, MAX_BLUR_RADIUS)
    }

    fun createFemaleBlur(bitmap: Bitmap): Bitmap? {
        return blurBitmap(bitmap, DEFAULT_BLUR_RADIUS)
    }
}