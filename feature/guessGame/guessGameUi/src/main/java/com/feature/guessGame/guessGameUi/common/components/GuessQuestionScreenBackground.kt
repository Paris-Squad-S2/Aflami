package com.feature.guessGame.guessGameUi.common.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.paris.aflami.designsystem.theme.Theme

@Composable
fun GuessGameBackground(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.colors.surface)
    ) {
        CircleBackground(xPercentage = -0.09f, yPercentage = 0.35f, size = 100.dp)
        CircleBackground(xPercentage = 0.70f, yPercentage = 0.41f, size = 64.dp)
        CircleBackground(xPercentage = 0.90f, yPercentage = 0.60f, size = 64.dp)
        CircleBackground(xPercentage = 0.5f, yPercentage = 0.70f, size = 32.dp)
        CircleBackground(xPercentage = 0.05f, yPercentage = 0.78f, size = 24.dp)
        CircleBackground(xPercentage = 0.68f, yPercentage = 0.85f, size = 24.dp)
        CircleBackground(xPercentage = 0.55f, yPercentage = -0.09f, size = 64.dp)
        CircleBackground(xPercentage = 0.1f, yPercentage = -0.09f, size = 32.dp)
        CircleBackground(xPercentage = 0.95f, yPercentage = -0.04f, size = 40.dp)
        CircleBackground(xPercentage = 0.88f, yPercentage = 0.10f, size = 64.dp)
        CircleBackground(xPercentage = -0.09f, yPercentage = 0.88f, size = 100.dp)

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Theme.colors.primary.copy(alpha = 0.24f),
                            Theme.colors.surface.copy(alpha = 0.5f),
                        ),
                        endY = 1700f
                    )
                )
        )

        content()
    }
}

@Composable
fun CircleBackground(
    xPercentage: Float,
    yPercentage: Float,
    size: Dp,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .size(size)
            .offset(
                x = with(LocalConfiguration.current) { (screenWidthDp * xPercentage).dp },
                y = with(LocalConfiguration.current) { (screenHeightDp * yPercentage).dp })

            .background(
                color = Theme.colors.status.backgroundCircles,
                shape = CircleShape
            )
    )
}
