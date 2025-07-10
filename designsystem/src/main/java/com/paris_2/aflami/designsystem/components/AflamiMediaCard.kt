package com.paris_2.aflami.designsystem.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.paris_2.aflami.designsystem.R
import com.paris_2.aflami.designsystem.theme.AflamiTheme
import com.paris_2.aflami.designsystem.theme.Theme

@Composable
fun AflamiMediaCard(
    modifier: Modifier = Modifier,
    imagePainter: Painter,
    rating: String = "",
    movieName: String = "",
    mediaType: String = "",
    year: String = "",
    clickable: Boolean = false,
    onClick: () -> Unit = {},
    mediaCardType: MediaCardType,
    showRating: Boolean = true,
    showGradientFilter: Boolean = false,
    cardWidth: Dp? = null,
    cardHeight: Dp? = null,
    showPlayButton: Boolean = false,
    onPlayButtonClick: () -> Unit = {}
) {
    val (finalCardWidth, finalCardHeight) = when (mediaCardType) {
        MediaCardType.UP_COMING -> (cardWidth ?: 328.dp) to (cardHeight ?: 196.dp)
        MediaCardType.NORMAL -> (cardWidth ?: 156.dp) to (cardHeight ?: 222.dp)
        MediaCardType.EPISODE -> (cardWidth ?: 116.dp) to (cardHeight ?: 78.dp)
        MediaCardType.SLIDER -> (cardWidth ?: 244.dp) to (cardHeight ?: 300.dp)
    }
    val clipRadius = when (mediaCardType) {
        MediaCardType.EPISODE -> 12.dp
        MediaCardType.SLIDER -> 24.dp
        else -> 16.dp
    }

    Box(
        modifier = modifier
            .width(finalCardWidth)
            .height(finalCardHeight)
            .border(
                width = 1.dp,
                color = Color.White.copy(alpha = 0.08f),
                shape = RoundedCornerShape(clipRadius)
            )
            .clip(RoundedCornerShape(clipRadius))
            .then(
                if (clickable) {
                    Modifier.clickable { onClick() }
                } else Modifier
            )

    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = imagePainter,
            contentDescription = "media poster",
            contentScale = ContentScale.FillBounds
        )

        if (showRating) {
            Row() {
                Spacer(modifier = Modifier.weight(1f))
                RatingCard(
                    rating = rating,
                    modifier = Modifier.padding(top = 4.dp, end = 4.dp)
                )
            }
        }

        if (showGradientFilter) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(finalCardHeight - 114.dp)
                    .align(Alignment.BottomCenter)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = Theme.colors.gradient.overly.asReversed()
                        )
                    )
            )
        }
        Column(
            modifier = Modifier.padding(top = finalCardHeight - 48.dp, start = 8.dp)
        ) {
            Text(
                text = movieName,
                style = Theme.textStyle.label.large,
                color = Theme.colors.onPrimaryColors.onPrimary,
                overflow = TextOverflow.Ellipsis,
            )
            DescriptionSeparator(
                firstText = mediaType,
                secondText = year,
                textColor = Theme.colors.onPrimaryColors.onPrimaryBody
            )

        }

        if (showPlayButton) {
            MediaPlayButton(
                modifier = Modifier.align(Alignment.Center),
                onButtonClick = onPlayButtonClick,
                buttonType = MediaButtonType.BIG
            )
        }

    }
}

enum class MediaCardType {
    NORMAL,
    UP_COMING,
    EPISODE,
    SLIDER
}

@PreviewLightDark
@Composable
fun PreviewMediaNormalCard() {
    AflamiTheme {
        AflamiMediaCard(
            imagePainter = painterResource(R.drawable.anime_movie),
            rating = 9.9.toString(),
            movieName = "Your Name",
            mediaType = "TV show",
            year = "2016",
            mediaCardType = MediaCardType.NORMAL,
            showGradientFilter = true
        )
    }

}

@PreviewLightDark
@Composable
fun PreviewMediaUpComingCard() {
    AflamiTheme {
        AflamiMediaCard(
            imagePainter = painterResource(R.drawable.anime_horizontal),
            rating = 9.toString(),
            movieName = "Grave of the Fireflies",
            mediaType = "TV show",
            year = "2016",
            mediaCardType = MediaCardType.UP_COMING,
            showGradientFilter = true
        )
    }

}

@PreviewLightDark
@Composable
fun PreviewMediaEpisodeCard() {
    AflamiTheme {
        AflamiMediaCard(
            imagePainter = painterResource(R.drawable.attack_on_titan),
            rating = 8.8.toString(),
            mediaCardType = MediaCardType.EPISODE
        )
    }

}

@PreviewLightDark
@Composable
fun PreviewMediaSliderCard() {
    AflamiTheme {
        AflamiMediaCard(
            imagePainter = painterResource(R.drawable.shniderlist_slider),
            rating = 8.toString(),
            mediaCardType = MediaCardType.SLIDER,
            showRating = true,
            showPlayButton = true
        )
    }

}

@PreviewLightDark
@Composable
fun PreviewMediaEmptySliderCard() {
    AflamiTheme {
        AflamiMediaCard(
            imagePainter = painterResource(R.drawable.shniderlist_slider),
            mediaCardType = MediaCardType.SLIDER,
            showRating = false,
            cardWidth = 207.dp,
            cardHeight = 276.dp,
            clickable = true
        )
    }

}