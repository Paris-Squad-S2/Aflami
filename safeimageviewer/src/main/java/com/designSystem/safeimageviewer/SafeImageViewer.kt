package com.designSystem.safeimageviewer

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.core.graphics.createBitmap
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.request.ImageRequest
import com.designSystem.safeimageviewer.modifier.blur
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun SafeImageViewer(
    imageUrl: String,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    contentScale: ContentScale = ContentScale.Crop,
    blurFemales: Boolean = true,
    blurNSFW: Boolean = true,
    nsfwThreshold: Float = 0.8f,
    genderThreshold: Float = 0.6f,
    onAnalysisComplete: ((ImageAnalysisResult) -> Unit)? = null,
    loadingContent: @Composable () -> Unit = { },
    errorContent: @Composable (String) -> Unit = { },
    isScrolling: Boolean = false
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val safeImageProcessor = remember { SafeImageProcessor(context) }
    val analysisCache = remember { mutableMapOf<String, ImageAnalysisResult>() }
    var imageState by remember { mutableStateOf<SafeImageState>(SafeImageState.Loading) }
    var originalBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var shouldBlur by remember { mutableStateOf(false) }
    var blurRadius by remember { mutableStateOf(0) }

    DisposableEffect(safeImageProcessor) {
        onDispose { safeImageProcessor.release() }
    }

    LaunchedEffect(imageUrl) {
        analysisCache[imageUrl]?.let { cached ->
            originalBitmap = cached.originalBitmap
            shouldBlur = cached.shouldBlur
            blurRadius = if (cached.shouldBlur) 100 else 0
            imageState = SafeImageState.Ready(cached.processedBitmap)
        }
    }

    when (val state = imageState) {
        is SafeImageState.Loading -> {
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(imageUrl)
                    .allowHardware(false)
                    .build(),
                contentDescription = contentDescription,
                modifier = modifier,
                contentScale = contentScale,
                onState = { asyncImageState ->
                    when (asyncImageState) {
                        is AsyncImagePainter.State.Success -> {
                            val bitmap = asyncImageState.result.drawable.toBitmapArgb8888()
                            originalBitmap = bitmap
                            imageState = SafeImageState.Analyzing

                            scope.launch(safeImageProcessor.getProcessingDispatcher()) {
                                try {
                                    val result = safeImageProcessor.processImageSync(
                                        bitmap = bitmap,
                                        blurFemales = blurFemales,
                                        blurNSFW = blurNSFW,
                                        nsfwThreshold = nsfwThreshold,
                                        genderThreshold = genderThreshold
                                    )

                                    analysisCache[imageUrl] = result

                                    shouldBlur = result.shouldBlur
                                    blurRadius = if (result.shouldBlur) 100 else 0
                                    imageState = SafeImageState.Ready(result.processedBitmap)

                                    withContext(Dispatchers.Main) {
                                        onAnalysisComplete?.invoke(result)
                                    }
                                } catch (e: Exception) {
                                    Log.e("SafeImageViewer", "Analysis failed: ${e.message}", e)
                                    imageState = SafeImageState.Error("Analysis failed")
                                }
                            }
                        }

                        is AsyncImagePainter.State.Error -> {
                            imageState = SafeImageState.Error("Failed to load image")
                        }

                        else -> {}
                    }
                }
            )

            Box(modifier = modifier, contentAlignment = Alignment.Center) { loadingContent() }
        }

        is SafeImageState.Analyzing -> {
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(imageUrl)
                    .allowHardware(false)
                    .build(),
                contentDescription = contentDescription,
                modifier = modifier,
                contentScale = contentScale
            )
        }

        is SafeImageState.Ready -> {
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(imageUrl)
                    .allowHardware(false)
                    .build(),
                contentDescription = contentDescription,
                modifier = modifier
                    .blur(
                        sourceBitmap = when {
                            isScrolling -> originalBitmap
                            shouldBlur -> originalBitmap
                            else -> null
                        },
                        radius = if (isScrolling) 50 else blurRadius,
                        enabled = isScrolling || shouldBlur
                    ),
                contentScale = contentScale
            )
        }

        is SafeImageState.Error -> {
            Box(modifier = modifier, contentAlignment = Alignment.Center) {
                errorContent(state.message)
            }
        }
    }
}

private fun Drawable.toBitmapArgb8888(): Bitmap {
    val rawBitmap = if (this is android.graphics.drawable.BitmapDrawable) {
        bitmap
    } else {
        val w = intrinsicWidth.takeIf { it > 0 } ?: 1
        val h = intrinsicHeight.takeIf { it > 0 } ?: 1
        val bmp = createBitmap(w, h)
        val canvas = android.graphics.Canvas(bmp)
        setBounds(0, 0, canvas.width, canvas.height)
        draw(canvas)
        bmp
    }
    return if (rawBitmap.config != Bitmap.Config.ARGB_8888) {
        rawBitmap.copy(Bitmap.Config.ARGB_8888, false)
    } else rawBitmap
}


sealed class SafeImageState {
    object Loading : SafeImageState()
    object Analyzing : SafeImageState()
    data class Ready(val bitmap: Bitmap) : SafeImageState()
    data class Error(val message: String) : SafeImageState()
}
