package com.designSystem.safeimageviewer

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.graphics.createBitmap
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun SafeImageViewer(
    model: Any,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    contentScale: ContentScale = ContentScale.Fit,
    blurRadius: Float = 20f,
    confidenceThreshold: Float = 0.7f,
    showLoadingIndicator: Boolean = true,
    placeholder: @Composable (() -> Unit)? = null,
) {
    val context = LocalContext.current
    var isNSFW by remember { mutableStateOf(false) }
    var drawable by remember { mutableStateOf<Drawable?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var isAnalyzing by remember { mutableStateOf(false) }
    val nsfwDetector = remember { NSFWDetector(context) }

    val coroutineExceptionHandler = remember {
        kotlinx.coroutines.CoroutineExceptionHandler { _, exception ->
            isNSFW = false
            isAnalyzing = false
        }
    }

    // Create image request
    val imageRequest = remember(model) {
        ImageRequest.Builder(context)
            .data(model)
            .size(Size.ORIGINAL)
            .crossfade(true)
            .build()
    }

    LaunchedEffect(drawable) {
        drawable?.let { drw ->
            isAnalyzing = true
            withContext(Dispatchers.IO + coroutineExceptionHandler) {
                try {
                    val bitmap = convertToARGB8888Bitmap(drw)
                    nsfwDetector.isNSFW(bitmap, confidenceThreshold) { isNSFWResult, _, _ ->
                        // Switch back to Main thread to update UI state
                        CoroutineScope(Dispatchers.Main).launch {
                            isNSFW = isNSFWResult
                            isAnalyzing = false
                        }
                    }
                } catch (_: Exception) {
                    withContext(Dispatchers.Main) {
                        isNSFW = false
                        isAnalyzing = false
                    }
                }
            }
        }
    }

    Box(modifier = modifier) {
        AsyncImage(
            model = imageRequest,
            contentDescription = contentDescription,
            modifier = Modifier
                .fillMaxSize()
                .then(
                    if (isNSFW) Modifier.blur(blurRadius.dp) else Modifier
                )
                .then(
                    if (isAnalyzing && drawable != null) {
                        Modifier.alpha(0f)
                    } else {
                        Modifier.alpha(1f)
                    }
                ),
            contentScale = contentScale,
            onState = { state ->
                when (state) {
                    is AsyncImagePainter.State.Loading -> {
                        isLoading = true
                        isAnalyzing = false
                    }

                    is AsyncImagePainter.State.Success -> {
                        isLoading = false
                        drawable = state.result.drawable
                    }

                    is AsyncImagePainter.State.Error -> {
                        isLoading = false
                        isAnalyzing = false
                    }

                    else -> {
                        isLoading = false
                        isAnalyzing = false
                    }
                }
            }
        )

        if ((isLoading || isAnalyzing) && showLoadingIndicator) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                placeholder?.invoke() ?: CircularProgressIndicator()
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
