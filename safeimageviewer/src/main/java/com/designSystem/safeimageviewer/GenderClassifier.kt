package com.designSystem.safeimageviewer

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import org.tensorflow.lite.DataType
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.support.common.FileUtil
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.MappedByteBuffer
import java.util.concurrent.ConcurrentHashMap

data class GenderResult(
    val isFemale: Boolean,
    val confidence: Float,
    val genderLabel: String
)

internal class GenderClassifier(private val context: Context) {
    private companion object {
        const val TAG = "GenderClassifier"
        const val MODEL_PATH = "gender_model.tflite"
        const val CONFIDENCE_THRESHOLD = 0.6f
        const val FEMALE_LABEL = "Female"
        const val MALE_LABEL = "Male"
    }

    private var interpreter: Interpreter? = null
    private var isModelLoaded = false
    private var inputSize = 224
    private var inputChannels = 3
    private var expectedInputDataType: org.tensorflow.lite.DataType = org.tensorflow.lite.DataType.UINT8

    private var imageProcessor: ImageProcessor? = null
    
    private val genderCache = ConcurrentHashMap<String, GenderResult>()

    init {
        loadModel()
    }

    private fun loadModel() {
        try {
            val modelBuffer: MappedByteBuffer = FileUtil.loadMappedFile(context, "GenderClass.tflite")
            interpreter = Interpreter(modelBuffer)
            
            val inputTensor = interpreter?.getInputTensor(0)
            val inputShape = inputTensor?.shape()
            val dataType = inputTensor?.dataType()
            expectedInputDataType = dataType ?: org.tensorflow.lite.DataType.UINT8
            
            if (inputShape != null && inputShape.size >= 3) {

                inputSize = inputShape[1]
                inputChannels = if (inputShape.size > 3) {

                    if (inputShape.size == 4) {
                        when {
                            inputShape[1] < inputShape[2] && inputShape[1] < inputShape[3] -> inputShape[1]
                            else -> inputShape[3]
                        }
                    } else {
                        3
                    }
                } else {
                    3
                }
                
                Log.d(TAG, "Model input shape: ${inputShape.contentToString()}, data type: $dataType")
                Log.d(TAG, "Using input size: $inputSize, channels: $inputChannels")
                
                val expectedBufferSize = when (expectedInputDataType) {
                    org.tensorflow.lite.DataType.FLOAT32 -> inputSize * inputSize * inputChannels * 4 // 4 bytes per float
                    org.tensorflow.lite.DataType.UINT8 -> inputSize * inputSize * inputChannels // 1 byte per uint8
                    else -> inputSize * inputSize * inputChannels
                }
                Log.d(TAG, "Expected buffer size: $expectedBufferSize bytes")
            }
            
            imageProcessor = ImageProcessor.Builder()
                .add(ResizeOp(inputSize, inputSize, ResizeOp.ResizeMethod.BILINEAR))
                .build()
            
            isModelLoaded = true
            Log.d(TAG, "Gender model loaded successfully")
        } catch (e: IOException) {
            Log.e(TAG, "Error loading gender model: ${e.message}")
            isModelLoaded = false
        }
    }

    fun classifyGender(
        bitmap: Bitmap,
        confidenceThreshold: Float = CONFIDENCE_THRESHOLD
    ): GenderResult? {
        if (!isModelLoaded) {
            Log.w(TAG, "Gender model not loaded, skipping gender classification")
            return null
        }

        return try {
            val argbBitmap = if (bitmap.config != Bitmap.Config.ARGB_8888) {
                Log.d(TAG, "Converting bitmap from ${bitmap.config} to ARGB_8888")
                bitmap.copy(Bitmap.Config.ARGB_8888, false)
            } else {
                bitmap
            }

            val processor = imageProcessor
            if (processor == null) {
                Log.e(TAG, "Image processor is null")
                return null
            }

            val tensorImage = TensorImage.fromBitmap(argbBitmap)
            val processedImage = processor.process(tensorImage)

            val inputBuffer = if (expectedInputDataType == DataType.FLOAT32) {
                convertUInt8ToFloat32(processedImage.buffer)
            } else {
                processedImage.buffer
            }

            Log.d(TAG, "Input buffer size: ${inputBuffer.capacity()} bytes, expected: ${inputSize * inputSize * inputChannels * if (expectedInputDataType == org.tensorflow.lite.DataType.FLOAT32) 4 else 1} bytes")

            val outputBuffer = TensorBuffer.createFixedSize(intArrayOf(1, 2), DataType.FLOAT32)

            interpreter?.run(inputBuffer, outputBuffer.buffer)

            val scores = outputBuffer.floatArray
            Log.d(TAG, "Raw model scores: [${scores[0]}, ${scores[1]}]")

            val femaleScore = scores[1]
            val maleScore = scores[0]

            val confidence = maxOf(femaleScore, maleScore)

            val isFemale = femaleScore > maleScore && confidence >= confidenceThreshold
            val genderLabel = if (isFemale) FEMALE_LABEL else MALE_LABEL

            Log.d(TAG, "Gender Classification Results:")
            Log.d(TAG, "- Female Score: $femaleScore")
            Log.d(TAG, "- Male Score: $maleScore")
            Log.d(TAG, "- Max Confidence: $confidence")
            Log.d(TAG, "- Threshold: $confidenceThreshold")
            Log.d(TAG, "- Female > Male: ${femaleScore > maleScore}")
            Log.d(TAG, "- Confidence >= Threshold: ${confidence >= confidenceThreshold}")
            Log.d(TAG, "- Final Predicted Gender: $genderLabel")
            Log.d(TAG, "- Will classify as female: $isFemale")

            GenderResult(
                isFemale = isFemale,
                confidence = confidence,
                genderLabel = genderLabel
            )

        } catch (e: Exception) {
            Log.e(TAG, "Error during gender classification: ${e.message}", e)
            null
        }
    }


    private fun convertUInt8ToFloat32(byteBuffer: ByteBuffer): ByteBuffer {
        val floatBuffer = ByteBuffer.allocateDirect(byteBuffer.capacity() * 4).order(ByteOrder.nativeOrder())
        
        byteBuffer.rewind()
        
        while (byteBuffer.hasRemaining()) {
            val byteValue = byteBuffer.get().toInt() and 0xFF
            val floatValue = byteValue / 255.0f
            floatBuffer.putFloat(floatValue)
        }
        
        floatBuffer.rewind()
        return floatBuffer
    }

    fun release() {
        interpreter?.close()
        interpreter = null
        isModelLoaded = false
        genderCache.clear()
    }
}