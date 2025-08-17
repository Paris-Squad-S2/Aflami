@file:OptIn(ExperimentalCoroutinesApi::class)

package com.designSystem.safeimageviewer

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi

data class ImageAnalysisResult(
    val originalBitmap: Bitmap,
    val processedBitmap: Bitmap,
    val isNSFW: Boolean,
    val nsfwConfidence: Float,
    val isFemale: Boolean,
    val genderConfidence: Float,
    val shouldBlur: Boolean,
    val blurReason: String
)

class SafeImageProcessor private constructor(context: Context) {
    companion object {
        const val TAG = "SafeImageProcessor"
        const val MAX_CONCURRENT_PROCESSING = 3

        @Volatile
        private var INSTANCE: SafeImageProcessor? = null

        fun getInstance(context: Context): SafeImageProcessor {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: SafeImageProcessor(context.applicationContext).also { INSTANCE = it }
            }
        }
    }

    private val nsfwDetector = NSFWDetector(context)
    private val genderClassifier = GenderClassifier(context)

    private val processingDispatcher: CoroutineDispatcher = Dispatchers.Default.limitedParallelism(MAX_CONCURRENT_PROCESSING)

    fun processImageSync(
        bitmap: Bitmap,
        blurFemales: Boolean = true,
        blurNSFW: Boolean = true,
        nsfwThreshold: Float = 0.8f,
        genderThreshold: Float = 0.6f
    ): ImageAnalysisResult {
        Log.d(TAG, "Starting sync image analysis - blurFemales: $blurFemales, blurNSFW: $blurNSFW")

        var isNSFW = false
        var nsfwConfidence = 0f
        var isFemale = false
        var genderConfidence = 0f
        var shouldBlur = false
        var blurReason = ""
        var processedBitmap = bitmap

        try {
            if (blurNSFW) {
                Log.d(TAG, "Checking for NSFW content...")
                var nsfwCompleted = false

                nsfwDetector.isNSFW(bitmap, nsfwThreshold) { nsfw, confidence, _ ->
                    isNSFW = nsfw
                    nsfwConfidence = confidence
                    nsfwCompleted = true
                    Log.d(TAG, "NSFW check complete - isNSFW: $nsfw, confidence: $confidence")
                }

                while (!nsfwCompleted) {
                    Thread.sleep(10)
                }

                if (isNSFW) {
                    shouldBlur = true
                    blurReason = "NSFW content detected (confidence: $nsfwConfidence)"
                    Log.d(TAG, "Will blur due to NSFW content")
                }
            }

            if (!shouldBlur && blurFemales) {
                Log.d(TAG, "Checking for gender classification...")
                val genderResult = genderClassifier.classifyGender(bitmap, genderThreshold)

                if (genderResult != null) {
                    isFemale = genderResult.isFemale
                    genderConfidence = genderResult.confidence

                    Log.d(TAG, "Gender classification complete - isFemale: $isFemale, confidence: $genderConfidence, threshold: $genderThreshold")

                    if (genderResult.isFemale && genderResult.confidence >= genderThreshold) {
                        shouldBlur = true
                        blurReason = "Female subject detected (confidence: $genderConfidence)"
                        Log.d(TAG, "Will blur due to female detection")
                    }
                } else {
                    Log.w(TAG, "Gender classification returned null result")
                }
            }

            if (shouldBlur) {
                Log.d(TAG, "Applying blur: $blurReason")
                processedBitmap = when {
                    isNSFW && blurNSFW -> {
                        Log.d(TAG, "Applying NSFW blur")
                        ImageBlurUtils.createNSFWBlur(bitmap) ?: bitmap
                    }
                    isFemale && blurFemales -> {
                        Log.d(TAG, "Applying female blur")
                        ImageBlurUtils.createFemaleBlur(bitmap) ?: bitmap
                    }
                    else -> bitmap
                }
            } else {
                Log.d(TAG, "No blur needed - NSFW: $isNSFW (conf: $nsfwConfidence), Female: $isFemale (conf: $genderConfidence)")
            }

            val result = ImageAnalysisResult(
                originalBitmap = bitmap,
                processedBitmap = processedBitmap,
                isNSFW = isNSFW,
                nsfwConfidence = nsfwConfidence,
                isFemale = isFemale,
                genderConfidence = genderConfidence,
                shouldBlur = shouldBlur,
                blurReason = blurReason
            )

            Log.d(TAG, "Image analysis complete. Should blur: $shouldBlur, Reason: $blurReason")
            return result

        } catch (e: Exception) {
            Log.e(TAG, "Error processing image: ${e.message}", e)
            return ImageAnalysisResult(
                originalBitmap = bitmap,
                processedBitmap = bitmap,
                isNSFW = false,
                nsfwConfidence = 0f,
                isFemale = false,
                genderConfidence = 0f,
                shouldBlur = false,
                blurReason = "Error during analysis: ${e.message}"
            )
        }
    }

    fun getProcessingDispatcher(): CoroutineDispatcher = processingDispatcher

    fun release() {
        try {
            nsfwDetector.close()
            genderClassifier.release()
        } catch (e: Exception) {
            Log.e(TAG, "Error releasing resources: ${e.message}")
        }
    }
}