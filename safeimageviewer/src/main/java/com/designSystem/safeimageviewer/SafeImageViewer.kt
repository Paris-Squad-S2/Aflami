package com.designSystem.safeimageviewer

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.graphics.createBitmap
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.designSystem.safeimageviewer.modifier.blur
import kotlinx.coroutines.launch

@Stable
internal class SafeImageState(
    painterState: AsyncImagePainter.State,
    shouldBlur: Boolean,
    sourceBitmap: Bitmap?,
    isAnalyzing: Boolean
) {
    var painterState by mutableStateOf(painterState)
    var shouldBlur by mutableStateOf(shouldBlur)
    var sourceBitmap by mutableStateOf(sourceBitmap)
    var isAnalyzing by mutableStateOf(isAnalyzing)
}

@Composable
internal fun rememberSafeImageState(
    context: android.content.Context,
    confidenceThreshold: Float
): SafeImageState = remember {
    SafeImageState(
        painterState = AsyncImagePainter.State.Empty,
        shouldBlur = true,
        sourceBitmap = null,
        isAnalyzing = false
    )
}.apply {
    val nsfwDetector = remember { NSFWDetector(context) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(painterState) {
        val currentState = painterState
        if (currentState is AsyncImagePainter.State.Success) {
            isAnalyzing = true
            val bitmap = convertToARGB8888Bitmap(currentState.result.drawable)
            sourceBitmap = bitmap
            coroutineScope.launch {
                nsfwDetector.isNSFW(bitmap, confidenceThreshold) { isNSFWResult, _, _ ->
                    shouldBlur = isNSFWResult
                    isAnalyzing = false
                }
            }
        } else {
            shouldBlur = true
            sourceBitmap = null
            isAnalyzing = false
        }
    }
}

@Composable
fun SafeImageViewer(
    model: Any,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    contentScale: ContentScale = ContentScale.Fit,
    blurRadius: Int = 50,
    confidenceThreshold: Float = 0.7f,
    showLoadingIndicator: Boolean = true,
    placeholder: @Composable (() -> Unit)? = null,
    errorPlaceholder: @Composable (() -> Unit)? = null
) {
    val context = LocalContext.current
    val state = rememberSafeImageState(context, confidenceThreshold)

    val isLoading = state.painterState is AsyncImagePainter.State.Loading
    val isError = state.painterState is AsyncImagePainter.State.Error

    Box(modifier = modifier) {
        AsyncImage(
            model = remember(model) {
                ImageRequest.Builder(context)
                    .data(model)
                    .size(Size.ORIGINAL)
                    .crossfade(true)
                    .build()
            },
            onState = { state.painterState = it },
            contentDescription = contentDescription,
            modifier = Modifier
                .fillMaxSize()
                .then(
                    if (state.sourceBitmap != null) {
                        Modifier.blur(
                            sourceBitmap = state.sourceBitmap,
                            radius = blurRadius,
                            enabled = state.shouldBlur
                        )
                    } else {
                        Modifier.blur(blurRadius.dp)
                    }
                ),
            contentScale = contentScale,
        )

        when {
            isLoading || state.isAnalyzing -> {
                if (showLoadingIndicator) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        placeholder?.invoke() ?: CircularProgressIndicator()
                    }
                }
            }

            isError -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    errorPlaceholder?.invoke()
                }
            }
        }
    }
}

/**
 * Converts a drawable to a bitmap in ARGB_8888 format, which is required by TensorFlow Lite.
 */
private fun convertToARGB8888Bitmap(drawable: Drawable): Bitmap {
    return when (drawable) {
        is BitmapDrawable -> {
            val originalBitmap = drawable.bitmap
            if (originalBitmap.config == Bitmap.Config.ARGB_8888) {
                originalBitmap
            } else {
                originalBitmap.copy(Bitmap.Config.ARGB_8888, false)
            }
        }

        else -> {
            val bitmap = createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight)
            val canvas = android.graphics.Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
            bitmap
        }
    }
}
