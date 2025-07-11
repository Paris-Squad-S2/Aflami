package com.paris_2.aflami.designsystem.components

import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.paris_2.aflami.designsystem.R
import com.paris_2.aflami.designsystem.theme.Theme

@Composable
fun RatingBar(
    modifier: Modifier = Modifier,
    rating: Float,
    maxRating: Int = 10,
    starSize: Dp = 24.dp,
    spaceBetween: Dp = 4.dp,
    selectedColor: Color = Theme.colors.status.yellowAccent,
    onRatingChange: (Float) -> Unit
) {
    val density = LocalDensity.current
    val layoutDirection = LocalLayoutDirection.current

    Row(
        modifier = modifier
            .height(starSize)
            .pointerInput(maxRating, starSize, spaceBetween) {
                detectHorizontalDragGestures { change, _ ->
                    val x = change.position.x
                    val starWidthPx = with(density) { starSize.toPx() }
                    val spaceBetweenPx = with(density) { spaceBetween.toPx() }
                    val totalStarWidth = starWidthPx + spaceBetweenPx
                    val effectiveX =
                        if (layoutDirection == LayoutDirection.Rtl) size.width - x else x
                    val calculatedRating =
                        (effectiveX / totalStarWidth).coerceIn(0f, maxRating.toFloat())
                    onRatingChange(calculatedRating)
                }
            }
            .pointerInput(maxRating, starSize, spaceBetween) {
                detectTapGestures { offset ->
                    val starWidthPx = with(density) { starSize.toPx() }
                    val spaceBetweenPx = with(density) { spaceBetween.toPx() }
                    val totalStarWidth = starWidthPx + spaceBetweenPx
                    val x =
                        if (layoutDirection == LayoutDirection.Rtl) size.width - offset.x else offset.x
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
            StarIconFromResource(
                modifier = Modifier.size(starSize),
                fillRatio = fillRatio,
                layoutDirection = layoutDirection,
                selectedColor = selectedColor
            )
        }
    }
}

@Composable
private fun StarIconFromResource(
    modifier: Modifier = Modifier,
    fillRatio: Float,
    layoutDirection: LayoutDirection,
    selectedColor: Color = Theme.colors.status.yellowAccent
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        val starModifier = Modifier.matchParentSize()

        Icon(
            painter = painterResource(id = R.drawable.ic_star_filled),
            contentDescription = null,
            tint = selectedColor.copy(alpha = 0.3f),
            modifier = starModifier
        )

        Icon(
            painter = painterResource(id = R.drawable.ic_star_filled),
            contentDescription = null,
            tint = selectedColor,
            modifier = starModifier
                .graphicsLayer {
                    compositingStrategy = CompositingStrategy.Offscreen
                }
                .drawWithContent {
                    clipRect(
                        right = if (layoutDirection == LayoutDirection.Rtl) size.width else size.width * fillRatio,
                        left = if (layoutDirection == LayoutDirection.Rtl) size.width - size.width * fillRatio else 0f) {
                        this@drawWithContent.drawContent()
                    }
                }
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