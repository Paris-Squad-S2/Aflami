package com.designSystem.safeimageviewer

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
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

    var imageState by remember { mutableStateOf<SafeImageState>(SafeImageState.Loading) }
    var originalBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var shouldBlur by remember { mutableStateOf(false) }
    var blurRadius by remember { mutableStateOf(15) }

    val safeImageProcessor = remember { SafeImageProcessor(context) }

    DisposableEffect(safeImageProcessor) {
        onDispose {
            safeImageProcessor.release()
        }
    }

    when (val state = imageState) {
        is SafeImageState.Loading -> {
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(imageUrl)
                    .build(),
                contentDescription = contentDescription,
                modifier = modifier,
                contentScale = contentScale,
                onState = { asyncImageState ->
                    when (asyncImageState) {


                        is AsyncImagePainter.State.Success -> {
                            val bitmap = asyncImageState.result.drawable.toBitmap()
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

                                    shouldBlur = result.shouldBlur
                                    blurRadius = when {
                                        result.isNSFW && blurNSFW -> 100
                                        result.isFemale && blurFemales -> 100
                                        else -> 0
                                    }

                                    imageState = SafeImageState.Ready(bitmap)

                                    withContext(Dispatchers.Main) {
                                        onAnalysisComplete?.invoke(result)
                                    }

                                } catch (e: Exception) {
                                    Log.e("SafeImageViewer", "Analysis failed: ${e.message}")
                                    imageState = SafeImageState.Error("Analysis failed: ${e.message}")
                                }
                            }
                        }

                        is AsyncImagePainter.State.Error -> {
                            imageState = SafeImageState.Error("Failed to load image")
                        }

                        else -> Unit
                    }
                }
            )
            Box(modifier = modifier, contentAlignment = Alignment.Center) {
                loadingContent()
            }
        }

        is SafeImageState.Analyzing -> {
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(imageUrl)
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
                    .build(),
                contentDescription = contentDescription,
                modifier = modifier
                    .blur(
                        sourceBitmap = if (isScrolling) originalBitmap else if (shouldBlur) originalBitmap else null,
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


private fun Drawable.toBitmap(): Bitmap {
    if (this is android.graphics.drawable.BitmapDrawable) {
        return bitmap
    }

    val bitmap = createBitmap(intrinsicWidth.takeIf { it > 0 } ?: 1, intrinsicHeight.takeIf { it > 0 } ?: 1)

    val canvas = android.graphics.Canvas(bitmap)
    setBounds(0, 0, canvas.width, canvas.height)
    draw(canvas)
    return bitmap
}

sealed class SafeImageState {
    object Loading : SafeImageState()
    object Analyzing : SafeImageState()
    data class Ready(val bitmap: Bitmap) : SafeImageState()
    data class Error(val message: String) : SafeImageState()
}
