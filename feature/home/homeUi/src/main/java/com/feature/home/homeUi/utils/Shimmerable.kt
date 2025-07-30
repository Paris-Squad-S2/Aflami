package com.feature.home.homeUi.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.paris_2.aflami.designsystem.theme.Theme
import com.valentinilk.shimmer.shimmer

@Composable
fun Modifier.shimmerable(
    shape: Shape = RoundedCornerShape(8.dp),
    color: Color = Theme.colors.surface.copy(alpha = 0.6f),
    enabled: Boolean = true
): Modifier {
    if (!enabled) return this

    return this
        .shimmer()
        .background(shape = shape, color = color)
        .drawBehind{

        }

}