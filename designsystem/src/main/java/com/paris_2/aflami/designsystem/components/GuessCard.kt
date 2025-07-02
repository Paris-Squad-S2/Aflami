package com.paris_2.aflami.designsystem.components

import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.paris_2.aflami.designsystem.R
import com.paris_2.aflami.designsystem.theme.Theme
import com.paris_2.aflami.designsystem.utils.BasePreview
import kotlin.math.ceil


@Composable
fun GuessCard(
    modifier: Modifier = Modifier,
    imagePainter: Painter? = null,
    textNoImage: String? = null,
    clickable: Boolean = false,
    onClick: () -> Unit = {},
    imageState: GuessCardImageState = GuessCardImageState.Show,
    showHint: Boolean = false,
    hintPoints: Int = 10
) {
    Column(
        modifier = modifier
            .background(
                color = Theme.colors.surface,
                shape = RoundedCornerShape(24.dp),
            )
            .clickable(enabled = clickable, onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .blur(
                    radius = when (imageState) {
                        GuessCardImageState.Hard -> 8.dp
                        GuessCardImageState.Medium -> 4.dp
                        GuessCardImageState.Show -> 0.dp
                    }
                )
                .then(if (imagePainter != null) Modifier.padding(8.dp) else Modifier)
                .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                .then(
                    if (!showHint) Modifier.clip(
                        RoundedCornerShape(
                            bottomStart = 20.dp,
                            bottomEnd = 20.dp
                        )
                    ) else Modifier
                ),

            ) {

            if (imagePainter != null) {
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(176.dp),
                    painter = imagePainter,
                    contentDescription = stringResource(R.string.character_image),
                    contentScale = ContentScale.Crop
                )
            } else if (textNoImage != null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(176.dp)
                        .background(
                            Brush.verticalGradient(
                                listOf(
                                    Theme.colors.surface,
                                    Color(0xFFD85895).copy(alpha = 0.12f),
                                    Color(0xFFD85895).copy(alpha = 0.12f),
                                    Theme.colors.surface,
                                )
                            )
                        )
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = textNoImage,
                        style = Theme.textStyle.title.large,
                        color = Theme.colors.text.title,
                    )
                }
            }

            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q) {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(176.dp)
                        .background(
                            Theme.colors.surface.copy(
                                alpha = when (imageState) {
                                    GuessCardImageState.Hard -> 0.9f
                                    GuessCardImageState.Medium -> 0.7f
                                    GuessCardImageState.Show -> 0f
                                }
                            )
                        )
                )
            }
        }
        if (showHint) {
            val context = LocalContext.current
            val drawable = ContextCompat.getDrawable(context, R.drawable.ic_slash_pattern)!!
            val bitmap = drawable.toBitmap()
            val imageBitmap = bitmap.asImageBitmap()
            val tintColor = Theme.colors.primary.copy(alpha = 0.03f)

            CompositionLocalProvider(
                LocalLayoutDirection provides if (LocalContext.current.resources.configuration.layoutDirection == android.util.LayoutDirection.RTL)
                    LayoutDirection.Rtl else LayoutDirection.Ltr,
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(32.dp)
                        .drawWithContent {
                            val tileWidthDp = 20.dp
                            val spacingDp = 20.dp

                            // Convert dp to pixels
                            val tileWidthPx = density.run { tileWidthDp.toPx() }
                            val spacingPx = density.run { spacingDp.toPx() }

                            val totalTileWidth = tileWidthPx + spacingPx
                            val horizontalTiles = ceil(size.width / totalTileWidth).toInt()

                            // Draw tiled background
                            for (i in 0 until horizontalTiles) {
                                drawImage(
                                    image = imageBitmap,
                                    topLeft = Offset(i * totalTileWidth, 0f),
                                    colorFilter = ColorFilter.tint(tintColor)
                                )
                            }

                            // Draw overlay content (your text/icon/etc.)
                            drawContent()
                        }
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(R.string.hint_text),
                        style = Theme.textStyle.label.small,
                        color = Theme.colors.status.yellowAccent,
                    )
                    Text(
                        text = hintPoints.toString() + " " + stringResource(R.string.pts),
                        style = Theme.textStyle.label.small,
                        color = Theme.colors.status.yellowAccent,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                    Icon(
                        painter = painterResource(if (imagePainter != null) R.drawable.ic_horisontal_star else R.drawable.ic_falling_star),
                        contentDescription = null,
                        modifier = Modifier.padding(start = 4.dp),
                        tint = Theme.colors.status.yellowAccent
                    )
                }
            }
        }
    }

}

enum class GuessCardImageState {
    Hard,
    Medium,
    Show,
}

@Composable
@PreviewLightDark
private fun GuessCharacterCardPreview() {
    BasePreview {
        var state by remember { mutableStateOf(GuessCardImageState.Hard) }
        Column {
            GuessCard(
                imagePainter = painterResource(R.drawable.img_guess_character),
                clickable = true,
                imageState = state,
                showHint = state == GuessCardImageState.Hard,
                onClick = {
                    state = when (state) {
                        GuessCardImageState.Hard -> GuessCardImageState.Medium
                        GuessCardImageState.Medium -> GuessCardImageState.Show
                        GuessCardImageState.Show -> GuessCardImageState.Hard
                    }
                },
            )

            HorizontalDivider(thickness = 5.dp)

            var state2 by remember { mutableStateOf(GuessCardImageState.Hard) }
            GuessCard(
                textNoImage = when (state2) {
                    GuessCardImageState.Hard -> "Guess"
                    GuessCardImageState.Medium -> "Hint"
                    GuessCardImageState.Show -> "Great! You guessed it!"
                },
                clickable = true,
                showHint = state2 == GuessCardImageState.Hard,
                onClick = {
                    state2 = when (state2) {
                        GuessCardImageState.Hard -> GuessCardImageState.Medium
                        GuessCardImageState.Medium -> GuessCardImageState.Show
                        GuessCardImageState.Show -> GuessCardImageState.Hard
                    }
                },
            )
        }
    }
}
