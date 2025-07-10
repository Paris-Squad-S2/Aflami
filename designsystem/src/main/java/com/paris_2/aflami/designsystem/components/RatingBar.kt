package com.paris_2.aflami.designsystem.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.paris_2.aflami.designsystem.theme.Theme
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun RatingBar(
    modifier: Modifier = Modifier,
    rating: Float,
    maxRating: Int = 10,
    starSize: Dp = 24.dp,
    spaceBetween: Dp = 4.dp,
    selectedColor: Color = Theme.colors.status.yellowAccent,
    unselectedColor: Color = Color.Transparent,
    strokeColor: Color = Theme.colors.status.yellowAccent,
    strokeWidth: Dp = 1.dp,
    onRatingChange: (Float) -> Unit
) {
    val density = LocalDensity.current

    Row(
        modifier = modifier
            .height(starSize + strokeWidth) // Adjust height to fit star and stroke
            .pointerInput(maxRating, starSize, spaceBetween) {
                detectHorizontalDragGestures { change, _ ->
                    val x = change.position.x
                    // Calculate the star index based on x position
                    val starWidthPx = with(density) { starSize.toPx() }
                    val spaceBetweenPx = with(density) { spaceBetween.toPx() }
                    val totalStarWidth = starWidthPx + spaceBetweenPx

                    val calculatedRating = (x / totalStarWidth).coerceIn(0f, maxRating.toFloat())
                    onRatingChange(calculatedRating)
                }
            }
            .pointerInput(maxRating, starSize, spaceBetween) {
                detectTapGestures { offset ->
                    val starWidthPx = with(density) { starSize.toPx() }
                    val spaceBetweenPx = with(density) { spaceBetween.toPx() }
                    val totalStarWidth = starWidthPx + spaceBetweenPx
                    val x = offset.x
                    val calculatedRating = (x / totalStarWidth).coerceIn(0f, maxRating.toFloat())
                    onRatingChange(calculatedRating)
                }
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(spaceBetween)
    ) {
        for (i in 1..maxRating) {
            val fillRatio = when {
                rating >= i -> 1f
                rating < i - 1 -> 0f
                else -> rating - (i - 1)
            }
            StarIcon(
                modifier = Modifier.size(starSize),
                selectedColor = selectedColor,
                unselectedColor = unselectedColor,
                strokeColor = strokeColor,
                strokeWidth = strokeWidth,
                fillRatio = fillRatio
            )
        }
    }
}

@Composable
private fun StarIcon(
    modifier: Modifier = Modifier,
    selectedColor: Color,
    unselectedColor: Color,
    strokeColor: Color,
    strokeWidth: Dp,
    fillRatio: Float // 0f to 1f
) {
    val density = LocalDensity.current
    Canvas(modifier = modifier) {
        val starPath = Path().apply {
            val width = size.width
            val height = size.height
            val centerX = width / 2f
            val centerY = height / 2f
            val outerRadius = width / 2f
            val innerRadius = outerRadius / 2.5f

            for (i in 0 until 5) {
                val angle = Math.toRadians((i * 72 - 90).toDouble())
                val xOuter = centerX + outerRadius * cos(angle).toFloat()
                val yOuter = centerY + outerRadius * sin(angle).toFloat()
                if (i == 0) moveTo(xOuter, yOuter) else lineTo(xOuter, yOuter)

                val angleInner = Math.toRadians((i * 72 - 90 + 36).toDouble())
                val xInner = centerX + innerRadius * cos(angleInner).toFloat()
                val yInner = centerY + innerRadius * sin(angleInner).toFloat()
                lineTo(xInner, yInner)
            }
            close()
            this.fillType = PathFillType.EvenOdd
        }

        // Draw the empty (unselected) star background
        drawPath(
            path = starPath,
            color = unselectedColor,
            style = Fill
        )

        // Draw the filled part based on fillRatio
        if (fillRatio > 0f) {
            clipRect(
                left = 0f,
                top = 0f,
                right = size.width * fillRatio.coerceIn(0f, 1f),
                bottom = size.height
            ) {
                drawPath(
                    path = starPath,
                    color = selectedColor,
                    style = Fill
                )
            }
        }

        // Draw the outline on top
        drawPath(
            path = starPath,
            color = strokeColor,
            style = Stroke(width = with(density) { strokeWidth.toPx() })
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewInteractiveRatingBarAdvanced() {
    var currentRating by remember { mutableFloatStateOf(7.7f) } // Test with 7.7 for a 3/4 star

    Column(Modifier.padding(16.dp)) {
        Text("IMDb rating", style = Theme.textStyle.title.small)
        Spacer(Modifier.height(8.dp))
        RatingBar(
            rating = currentRating,
            maxRating = 10,
            onRatingChange = { newRating ->
                currentRating = newRating
            }
        )
        Spacer(Modifier.height(8.dp))
        Text("Current Rating: %.1f / 10".format(currentRating))
    }
}