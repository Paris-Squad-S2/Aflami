package com.feature.guessGame.guessGameUi.common.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.paris.aflami.designsystem.theme.AflamiTheme
import com.paris.aflami.designsystem.theme.Theme


@Composable
fun QuestionIndicator(
    numberOfQuestions: Int,
    step: Int,
    modifier: Modifier = Modifier
) {
    val layoutDirection = LocalLayoutDirection.current

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(6.dp)
    ) {
        val indices = if (layoutDirection == LayoutDirection.Ltr) {
            0 until numberOfQuestions
        } else {
            (numberOfQuestions - 1 downTo 0)
        }

        indices.forEach { index ->
            IndicatorBox(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 2.dp),
                isSelect = if (layoutDirection == LayoutDirection.Ltr) {
                    index <= step
                } else {
                    index >= numberOfQuestions - 1 - step
                }
            )
        }
    }
}

@Composable
private fun IndicatorBox(
    modifier: Modifier = Modifier,
    isSelect: Boolean = false
) {
    val layoutDirection = LocalLayoutDirection.current
    val progress = remember { Animatable(0f) }

    LaunchedEffect(isSelect) {
        if (isSelect) {
            progress.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 800, easing = LinearEasing)
            )
        } else {
            progress.snapTo(0f)
        }
    }

    val startColor = Theme.colors.onPrimaryColors.onPrimary
    val endColor = Theme.colors.onPrimaryColors.onPrimaryHint

    Box(
        modifier = modifier
            .height(6.dp)
            .fillMaxWidth()
            .border(1.dp, endColor, CircleShape)
            .clip(CircleShape)
            .background(endColor)
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .then(
                    if (layoutDirection == LayoutDirection.Ltr) {
                        Modifier.fillMaxWidth(progress.value)
                    } else {
                        Modifier
                            .fillMaxWidth(progress.value)
                            .align(Alignment.CenterEnd)
                    }
                )
                .background(startColor)
        )
    }
}

// another version of IndictorBox with animation
// This version uses a gradient effect for the background
@Composable
private fun IndicatorBox2(
    modifier: Modifier = Modifier,
    isSelect: Boolean = false
) {
    val progress = remember { Animatable(0f) }

    LaunchedEffect(isSelect) {
        if (isSelect) {
            progress.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 800, easing = LinearEasing)
            )
        } else {
            progress.animateTo(
                targetValue = 0f,
                animationSpec = tween(durationMillis = 800, easing = LinearEasing)
            )
        }
    }

    val startColor = Theme.colors.onPrimaryColors.onPrimaryHint
    val endColor = Theme.colors.onPrimaryColors.onPrimary
    val animatedColor = lerp(startColor, endColor, progress.value)

    Box(
        modifier = modifier
            .height(6.dp)
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = animatedColor,
                shape = CircleShape
            )
            .clip(CircleShape)
            .background(animatedColor)
    )
}


@Preview
@Composable
private fun PreviewIndicatorBox() {
    var isSelect by remember { mutableStateOf(false) }
    AflamiTheme {
        Box(Modifier.clickable { isSelect = !isSelect }) {
            IndicatorBox(isSelect = isSelect)
        }
    }
}

@Preview
@Composable
private fun Preview() {
    AflamiTheme {
        QuestionIndicator(
            numberOfQuestions = 5,
            step = 1
        )
    }
}