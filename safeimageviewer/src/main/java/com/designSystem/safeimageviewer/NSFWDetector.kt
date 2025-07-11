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

    /**
     * This function returns whether the bitmap is NSFW or not
     * @param bitmap: Bitmap Image
     * @param confidenceThreshold: Float 0 to 1 (Default is 0.7)
     * @param callback: Callback with isNSFW(Boolean), confidence(Float), and image(Bitmap)
     */
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

        try {
            val threshold = if (confidenceThreshold in 0.0f..1.0f) {
                confidenceThreshold
            } else {
                CONFIDENCE_THRESHOLD
            }

            val tensorImage = TensorImage.fromBitmap(bitmap)
            val processedImage = imageProcessor.process(tensorImage)

            val outputBuffer = TensorBuffer.createFixedSize(
                interpreter!!.getOutputTensor(0).shape(),
                interpreter!!.getOutputTensor(0).dataType()
            )

            interpreter!!.run(processedImage.buffer, outputBuffer.buffer)

            val labeledProbability = TensorLabel(labels, outputBuffer).mapWithFloatValue.toMap()

            val maxEntry = labeledProbability.maxByOrNull { it.value }

            if (maxEntry != null) {
                val label = maxEntry.key
                val confidence = maxEntry.value

                Log.d(TAG, "Detected: $label with confidence: $confidence")

                when (label.lowercase()) {
                    "nude" -> {
                        val isNSFW = confidence >= threshold
                        callback(isNSFW, confidence, bitmap)
                    }
                    "nonnude" -> {
                        val isNSFW = confidence < threshold
                        callback(isNSFW, 1.0f - confidence, bitmap)
                    }
                    else -> {
                        callback(false, 0.0f, bitmap)
                    }
                }
            } else {
                callback(false, 0.0f, bitmap)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error during inference: ${e.message}")
            callback(false, 0.0f, bitmap)
        }
    }

    fun close() {
        interpreter?.close()
        interpreter = null
        isModelLoaded = false
    }
}
