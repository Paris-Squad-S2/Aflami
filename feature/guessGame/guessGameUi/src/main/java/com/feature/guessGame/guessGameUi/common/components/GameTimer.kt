package com.feature.guessGame.guessGameUi.common.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.paris_2.aflami.designsystem.components.AppText
import com.paris_2.aflami.designsystem.theme.AflamiTheme
import com.paris_2.aflami.designsystem.theme.Theme

@Composable
fun GameTimer(
    totalSeconds: Int,
    modifier: Modifier = Modifier,
    strokeWidth: Dp = 4.dp,
    backgroundCircleColor: Color = Theme.colors.primaryVariant,
    progressColor: Color = Theme.colors.status.greenAccent,
    onFinished: () -> Unit = {}
) {
    val overTimerColor = Theme.colors.status.redAccent
    val animatedProgress = remember { Animatable(1f) }

    LaunchedEffect(totalSeconds) {
        if (totalSeconds > 0) {
            animatedProgress.animateTo(
                targetValue = 0f,
                animationSpec = tween(
                    durationMillis = totalSeconds * 1000,
                    easing = LinearEasing
                )
            )
            onFinished()
        }
    }

    val timeLeft = (animatedProgress.value * totalSeconds).toInt().coerceAtLeast(0)

    val currentProgressColor = if (timeLeft <= 5) overTimerColor else progressColor
    val currentTextColor = if (timeLeft <= 5) overTimerColor else progressColor

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(43.5.dp)
            .clip(CircleShape)
    ) {
        Box(
            modifier = Modifier
                .size(42.dp)
                .clip(CircleShape)
                .background(backgroundCircleColor)
        )

        Canvas(modifier = Modifier.size(40.dp)) {
            drawArc(
                color = currentProgressColor,
                startAngle = -90f,
                sweepAngle = 360f * animatedProgress.value,
                useCenter = false,
                style = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round)
            )
        }

        AppText(
            text = "${timeLeft}s",
            color = currentTextColor,
            fontSize = 10.sp,
            style = Theme.textStyle.label.small
        )
    }
}


@Preview
@PreviewLightDark
@Composable
private fun Preview() {
    AflamiTheme {
        Box(
            modifier = Modifier,
            contentAlignment = Alignment.Center
        ) {
            GameTimer(
                totalSeconds = 10,
                modifier = Modifier,
            )
        }
    }
}