package com.paris_2.aflami.designsystem.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
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
import kotlin.math.cos
import kotlin.math.floor
import kotlin.math.sin

//TODO: Needs some refactor and make it smother
@Composable
fun RatingBar(
    modifier: Modifier = Modifier,
    rating: Float, // Use Float for half stars
    maxRating: Int = 10,
    starSize: Dp = 24.dp,
    spaceBetween: Dp = 4.dp, // Space between stars
    selectedColor: Color = MaterialTheme.colorScheme.tertiary,
    unselectedColor: Color = Color.Gray,
    strokeColor: Color = Color.DarkGray, // For outlined stars
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
            .clickable(
                onClick = {
                    // No-op, required for accessibility
                },
                onClickLabel = null,
                role = null,
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(spaceBetween)
    ) {
        for (i in 1..maxRating) {
            val fullStars = floor(rating).toInt()
            val fractionalPart = rating - fullStars

            val isFullStar = rating >= i
            val isHalfStar =
                (i == fullStars + 1) && (fractionalPart >= 0.25f && fractionalPart < 0.75f)
            val isThreeQuarterStar =
                (i == fullStars + 1) && (fractionalPart >= 0.75f) // For more precise visual representation
            val isQuarterStar =
                (i == fullStars + 1) && (fractionalPart > 0f && fractionalPart < 0.25f) // For less than half

            StarIcon(
                modifier = Modifier.size(starSize),
                selectedColor = selectedColor,
                unselectedColor = unselectedColor,
                strokeColor = strokeColor,
                strokeWidth = strokeWidth,
                fillType = when {
                    isFullStar -> StarFillType.FULL
                    isThreeQuarterStar -> StarFillType.THREE_QUARTER
                    isHalfStar -> StarFillType.HALF
                    isQuarterStar -> StarFillType.QUARTER
                    else -> StarFillType.EMPTY
                }
            )
        }
    }
}

// Enum to better manage star fill types
enum class StarFillType {
    EMPTY, QUARTER, HALF, THREE_QUARTER, FULL
}

@Composable
private fun StarIcon(
    modifier: Modifier = Modifier,
    selectedColor: Color,
    unselectedColor: Color,
    strokeColor: Color,
    strokeWidth: Dp,
    fillType: StarFillType // Use the enum
) {
    val density = LocalDensity.current
    Canvas(modifier = modifier) {
        val starPath = Path().apply {
            val width = size.width
            val height = size.height
            val centerX = width / 2f
            val centerY = height / 2f
            val outerRadius = width / 2f
            val innerRadius = outerRadius / 2.5f // Adjust for desired star arm thickness

            // Calculate points for a 5-pointed star
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
            // Set the fill type for the path itself for proper rendering of overlapping shapes
            // PathFillType.EvenOdd is typically good for stars
            this.fillType = PathFillType.EvenOdd
        }

        // 1. Draw the empty (unselected) star background
        drawPath(
            path = starPath,
            color = unselectedColor,
            style = Fill
        )

        // 2. Draw the filled part based on fillType using clipRect
        when (fillType) {
            StarFillType.FULL -> {
                drawPath(
                    path = starPath,
                    color = selectedColor,
                    style = Fill
                )
            }

            StarFillType.HALF -> {
                clipRect(
                    left = 0f,
                    top = 0f,
                    right = size.width / 2f, // Clip to half the width
                    bottom = size.height
                ) {
                    drawPath(
                        path = starPath,
                        color = selectedColor,
                        style = Fill
                    )
                }
            }

            StarFillType.QUARTER -> {
                clipRect(
                    left = 0f,
                    top = 0f,
                    right = size.width / 4f, // Clip to a quarter of the width
                    bottom = size.height
                ) {
                    drawPath(
                        path = starPath,
                        color = selectedColor,
                        style = Fill
                    )
                }
            }

            StarFillType.THREE_QUARTER -> {
                clipRect(
                    left = 0f,
                    top = 0f,
                    right = size.width * 3f / 4f, // Clip to three quarters of the width
                    bottom = size.height
                ) {
                    drawPath(
                        path = starPath,
                        color = selectedColor,
                        style = Fill
                    )
                }
            }

            StarFillType.EMPTY -> {
                // Do nothing, already drawn unselected background
            }
        }

        // 3. Draw the outline on top
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
        Text("IMDb rating", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(8.dp))
        RatingBar(
            rating = currentRating,
            onRatingChange = { newRating ->
                currentRating = newRating
            }
        )
        Spacer(Modifier.height(8.dp))
        Text("Current Rating: %.1f / 10".format(currentRating))
    }
}