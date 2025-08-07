package com.designSystem.safeimageviewer

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.support.common.FileUtil
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.support.label.TensorLabel
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.IOException
import java.nio.MappedByteBuffer
import java.util.concurrent.ConcurrentHashMap

const val TAG = "NSFWDetector"

internal class NSFWDetector(private val context: Context) {
    private companion object {
        const val MODEL_PATH = "NSFW.tflite"
        const val LABELS_PATH = "dict.txt"
        const val CONFIDENCE_THRESHOLD = 0.8f
        const val INPUT_SIZE = 224
    }

    private var interpreter: Interpreter? = null
    private var labels: List<String> = emptyList()
    private var isModelLoaded = false

    private val imageProcessor = ImageProcessor.Builder()
        .add(ResizeOp(INPUT_SIZE, INPUT_SIZE, ResizeOp.ResizeMethod.BILINEAR))
        .build()
        
    private val nsfwCache = ConcurrentHashMap<String, Pair<Boolean, Float>>()

    init {
        loadModel()
    }

    private fun loadModel() {
        try {
            val modelBuffer: MappedByteBuffer = FileUtil.loadMappedFile(context, MODEL_PATH)
            interpreter = Interpreter(modelBuffer)

            labels = FileUtil.loadLabels(context, LABELS_PATH)

            isModelLoaded = true
            Log.d(TAG, "Model loaded successfully")
        } catch (e: IOException) {
            Log.e(TAG, "Error loading model: ${e.message}")
            isModelLoaded = false
        }
    }


    fun isNSFW(
        bitmap: Bitmap,
        confidenceThreshold: Float = CONFIDENCE_THRESHOLD,
        callback: (Boolean, Float, Bitmap) -> Unit
    ) {
        if (!isModelLoaded) {
            Log.e(TAG, "Model not loaded")
            callback(false, 0.0f, bitmap)
            return
        }
        
        val cacheKey = "${bitmap.width}x${bitmap.height}_${bitmap.byteCount}"
        
        nsfwCache[cacheKey]?.let { cachedResult ->
            callback(cachedResult.first, cachedResult.second, bitmap)
            return
        }

        try {
            val argbBitmap = if (bitmap.config != Bitmap.Config.ARGB_8888) {
                bitmap.copy(Bitmap.Config.ARGB_8888, false)
            } else {
                bitmap
            }

            val tensorImage = TensorImage.fromBitmap(argbBitmap)
            val processedImage = imageProcessor.process(tensorImage)

            val outputBuffer = TensorBuffer.createFixedSize(intArrayOf(1, labels.size), org.tensorflow.lite.DataType.FLOAT32)

            interpreter?.run(processedImage.buffer, outputBuffer.buffer)

            val labeledOutput = TensorLabel(labels, outputBuffer)
            val scores = labeledOutput.mapWithFloatValue

            val maxEntry = scores.maxByOrNull { it.value }
            val isNSFWResult = maxEntry?.let { entry ->
                val isNSFW = entry.key.lowercase().contains("porn") ||
                           entry.key.lowercase().contains("sexy") ||
                           entry.key.lowercase().contains("nsfw")
                isNSFW && entry.value >= confidenceThreshold
            } ?: false

            val confidence = maxEntry?.value ?: 0.0f
            
            nsfwCache[cacheKey] = Pair(isNSFWResult, confidence)

            Log.d(TAG, "NSFW Detection - IsNSFW: $isNSFWResult, Confidence: $confidence")
            callback(isNSFWResult, confidence, bitmap)

        } catch (e: Exception) {
            Log.e(TAG, "Error during NSFW detection: ${e.message}")
            callback(false, 0.0f, bitmap)
        }
    }

    fun close() {
        interpreter?.close()
        interpreter = null
        isModelLoaded = false
        nsfwCache.clear()
    }
}