package com.designSystem.safeimageviewer.modifier

import android.graphics.Bitmap
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import com.designSystem.safeimageviewer.algorithm.blurBitmap

@Stable
fun Modifier.blur(
    sourceBitmap: Bitmap?,
    radius: Int = 50,
    enabled: Boolean = true
): Modifier = composed {
    var blurredBitmap by remember { mutableStateOf<ImageBitmap?>(null) }

    LaunchedEffect(sourceBitmap, radius, enabled) {
        blurredBitmap = if (sourceBitmap != null && enabled) {
            blurBitmap(sourceBitmap, radius).asImageBitmap()
        } else {
            null
        }
    }

    if (blurredBitmap != null && enabled) {
        this.then(
            Modifier.drawWithContent {
                drawIntoCanvas { canvas ->
                    canvas.nativeCanvas.drawBitmap(
                        blurredBitmap!!.asAndroidBitmap(),
                        null,
                        android.graphics.Rect(0, 0, size.width.toInt(), size.height.toInt()),
                        null
                    )
                }
            }
        )
    } else {
        this
    }
}
