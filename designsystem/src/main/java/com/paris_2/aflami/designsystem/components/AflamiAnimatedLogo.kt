package com.paris_2.aflami.designsystem.components


import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.paris_2.aflami.designsystem.R
import com.paris_2.aflami.designsystem.text_style.nicoMoji
import com.paris_2.aflami.designsystem.theme.Theme
import kotlinx.coroutines.delay

@Composable
fun AflamiAnimatedLogo() {
    var showFirst by remember { mutableStateOf(false) }
    var showSecond by remember { mutableStateOf(false) }
    var showThird by remember { mutableStateOf(false) }
    var startTextAnimation by remember { mutableStateOf(false) }

    val offsetY by animateDpAsState(
        targetValue = if (startTextAnimation) 40.dp else (-40).dp,
        animationSpec = tween(durationMillis = 400, easing = FastOutSlowInEasing)
    )

    val alpha by animateFloatAsState(
        targetValue = if (startTextAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = 400, easing = FastOutSlowInEasing)
    )

    val alpha1 by animateFloatAsState(
        targetValue = if (showFirst) 1f else 0f,
        animationSpec = tween(durationMillis = 800)
    )

    val alpha2 by animateFloatAsState(
        targetValue = if (showSecond) 0.5f else 0f,
        animationSpec = tween(durationMillis = 800)
    )

    val alpha3 by animateFloatAsState(
        targetValue = if (showThird) 0.3f else 0f,
        animationSpec = tween(durationMillis = 800)
    )

    val rotation2 by animateFloatAsState(
        targetValue = if (showSecond) -15f else -16f,
        animationSpec = tween(durationMillis = 800)
    )

    val rotation3 by animateFloatAsState(
        targetValue = if (showThird) -30f else -26f,
        animationSpec = tween(durationMillis = 800)
    )

    LaunchedEffect(Unit) {
        delay(800)
        showFirst = true
        delay(800)
        showSecond = true
        delay(800)
        showThird = true
        delay(800)
        startTextAnimation = true
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier.padding(bottom = 60.dp)
        ) {
            if (showFirst) {
                TriangleImage(
                    rotation = 0f,
                    alpha = alpha1,
                    zIndex = 2f
                )
            }
            if (showSecond) {
                TriangleImage(
                    rotation = rotation2,
                    alpha = alpha2,
                    zIndex = 1f,
                    offsetX = (-8).dp,
                    offsetY = (-6).dp
                )
            }
            if (showThird) {
                TriangleImage(
                    rotation = rotation3,
                    alpha = alpha3,
                    zIndex = 0f,
                    offsetX = (-16).dp,
                    offsetY = (-8).dp
                )
            }
        }

        Text(
            text = stringResource(R.string.aflami),
            modifier = Modifier
                .offset(y = offsetY, x = (-10).dp)
                .graphicsLayer {
                    this.alpha = alpha
                }
                .zIndex(3f),
            style = TextStyle(
                brush = Brush.linearGradient(Theme.colors.gradient.logoGradient),
                fontSize = 24.sp,
                fontFamily = nicoMoji,
            )
        )
    }
}

@Composable
fun TriangleImage(
    rotation: Float,
    alpha: Float,
    zIndex: Float,
    offsetX: Dp = 0.dp,
    offsetY: Dp = 0.dp
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.play_media),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.Center)
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

}

@Preview
@Composable
private fun Preview() {
    AflamiAnimatedLogo()
}
