package com.paris_2.aflami.designsystem.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.paris_2.aflami.designsystem.R
import com.paris_2.aflami.designsystem.text_style.nicoMoji
import kotlinx.coroutines.delay

@Composable
fun AflamiAnimatedLogo() {
    var showFirst by remember { mutableStateOf(false) }
    var showSecond by remember { mutableStateOf(false) }
    var showThird by remember { mutableStateOf(false) }
    var showText by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        showFirst = true
        delay(800)
        showSecond = true
        delay(800)
        showThird = true
        delay(1500)
        showText = true
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .padding(bottom = 60.dp)
        )
        {
            if (showFirst) {
                TriangleImage(
                    rotation = 0f,
                    alpha = 1f,
                    zIndex = 2f
                )
            }
            if (showSecond) {
                TriangleImage(
                    rotation = -15f,
                    alpha = 0.5f,
                    zIndex = 1f,
                    offsetX = (-8).dp,
                    offsetY = (-6).dp
                )
            }
            if (showThird) {
                TriangleImage(
                    rotation = -30f,
                    alpha = 0.3f,
                    zIndex = 0f,
                    offsetX = (-16).dp,
                    offsetY = (-8).dp

                )
            }
        }

        AnimatedVisibility(
            visible = showText,
            modifier = Modifier.align(Alignment.BottomCenter),
            enter =
                fadeIn(
                    animationSpec = tween(durationMillis = 200, easing = LinearEasing)
                ) +
                        slideInVertically(
                            initialOffsetY = { fullHeight -> -fullHeight / 2 },
                            animationSpec = tween(durationMillis = 700, easing = LinearEasing)
                        ) +
                        scaleIn(
                            initialScale = 0f,
                            animationSpec = tween(durationMillis = 200, easing = LinearEasing)
                        )
        ) {
            Text(
                text = "AFLAMI",
                fontSize = 32.sp,
                color = Color(0xFFE91E63),
                modifier = Modifier
                    .padding(bottom = 50.dp),
                fontFamily = nicoMoji,
                style = TextStyle(
                    brush = gradient
                )
            )
        }
    }
}

val gradient = Brush.linearGradient(
    colors = listOf(
        Color(0xD85895).copy(alpha = 0.87f),
        Color(0x64163B).copy(alpha = 0.87f)
    ),
    start = androidx.compose.ui.geometry.Offset.Zero,
    end = androidx.compose.ui.geometry.Offset.Infinite
)

@Composable
fun TriangleImage(
    rotation: Float,
    alpha: Float,
    zIndex: Float,
    offsetX: Dp = 0.dp,
    offsetY: Dp = 0.dp
) {
    Image(
        painter = painterResource(id = R.drawable.play_media),
        contentDescription = null,
        modifier = Modifier
            .width(62.dp)
            .height(70.dp)
            .offset(x = offsetX, y = offsetY)
            .zIndex(zIndex)
            .graphicsLayer {
                this.rotationZ = rotation
                this.alpha = alpha
            }
    )
}

@Preview
@Composable
fun Preview() {
    AflamiAnimatedLogo()
}
