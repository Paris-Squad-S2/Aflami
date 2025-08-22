package com.feature.home.homeUi.screen.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.shimmer

@Composable
fun Modifier.shimmerable(
    shape: Shape = RoundedCornerShape(8.dp),
    color: Color = White.copy(alpha = 0.24f),
    enabled: Boolean = true
): Modifier {
    if (!enabled) return this

    return this
        .shimmer()
        .background(shape = shape, color = color)
        .drawWithContent{}
}