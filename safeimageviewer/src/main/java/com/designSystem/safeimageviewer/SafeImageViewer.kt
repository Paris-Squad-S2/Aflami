package com.designSystem.safeimageviewer

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.designSystem.safeimageviewer.modifier.blur

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
    var isNSFW by remember { mutableStateOf(true) }
    var drawable by remember { mutableStateOf<Drawable?>(null) }
    var sourceBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var isError by remember { mutableStateOf(false) }
    var isAnalyzing by remember { mutableStateOf(false) }
    val nsfwDetector = remember { NSFWDetector(context) }

    val coroutineExceptionHandler = remember {
        kotlinx.coroutines.CoroutineExceptionHandler { _, exception ->
            isNSFW = true
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
            withContext(Dispatchers.Default + coroutineExceptionHandler) {
                try {
                    val bitmap = convertToARGB8888Bitmap(drw)
                    sourceBitmap = bitmap
                    nsfwDetector.isNSFW(bitmap, confidenceThreshold) { isNSFWResult, _, _ ->
                        // Switch back to Main thread to update UI state
                        CoroutineScope(Dispatchers.Main).launch {
                            isNSFW = isNSFWResult // Now unblur if safe
                            isAnalyzing = false
                        }
                    }
                } catch (_: Exception) {
                    withContext(Dispatchers.Main) {
                        isNSFW = true
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
                    if (sourceBitmap != null) {
                        Modifier.blur(
                            sourceBitmap = sourceBitmap,
                            radius = blurRadius,
                            enabled = isNSFW || isLoading || isAnalyzing
                        )
                    } else {
                        // Use built-in blur while bitmap is not ready
                        if (isNSFW || isLoading || isAnalyzing) {
                            Modifier.blur(blurRadius.dp)
                        } else {
                            Modifier
                        }
                    }
                ),
            contentScale = contentScale,
            onState = { state ->
                when (state) {
                    is AsyncImagePainter.State.Loading -> {
                        isLoading = true
                        isError = false
                        isAnalyzing = false
                        isNSFW = true
                        sourceBitmap = null
                    }

                    is AsyncImagePainter.State.Success -> {
                        isLoading = false
                        isError = false
                        drawable = state.result.drawable
                        val bitmap = convertToARGB8888Bitmap(state.result.drawable)
                        sourceBitmap = bitmap
                    }

                    is AsyncImagePainter.State.Error -> {
                        isLoading = false
                        isError = true
                        isAnalyzing = false
                        isNSFW = true
                        sourceBitmap = null
                    }

                    else -> {
                        isLoading = false
                        isError = false
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
        if (isError) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                errorPlaceholder?.invoke() ?: CircularProgressIndicator()
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
