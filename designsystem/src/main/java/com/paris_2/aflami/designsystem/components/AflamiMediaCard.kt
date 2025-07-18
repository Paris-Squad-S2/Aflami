package com.paris_2.aflami.designsystem.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.designSystem.safeimageviewer.SafeImageViewer
import com.paris_2.aflami.designsystem.R
import com.paris_2.aflami.designsystem.theme.AflamiTheme
import com.paris_2.aflami.designsystem.theme.Theme

@Composable
fun AflamiMediaCard(
    modifier: Modifier = Modifier,
    imageUri: String,
    rating: Float,
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
    onPlayButtonClick: () -> Unit = {},
    enabled: Boolean = true
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
        SafeImageViewer(
            model = imageUri,
            modifier = Modifier.fillMaxSize(),
            contentDescription = "media poster",
            contentScale = ContentScale.Crop,
            enabled = enabled,
            placeholder = {
                Image(
                    painter = painterResource(id = R.drawable.ic_film_roll),
                    contentDescription = null,
                    modifier = Modifier.size(48.dp),
                    contentScale = ContentScale.Crop
                )
            },
            errorPlaceholder = {
                Image(
                    painter = painterResource(id = R.drawable.img_disconnect),
                    contentDescription = null,
                    modifier = Modifier.size(48.dp),
                    contentScale = ContentScale.Crop
                )
            }
        )

        if (showRating) {
            RatingCard(
                rating = rating,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 4.dp, end = 4.dp)
            )
        }

        if (showGradientFilter) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(finalCardHeight - 114.dp)
                    .align(Alignment.BottomCenter)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = Theme.colors.gradient.overlyDark.asReversed()
                        )
                    )
            )
        }
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(
                    top = finalCardHeight - 48.dp,
                    start = 8.dp,
                    end = 8.dp,
                    bottom = 8.dp
                ),
            verticalArrangement = Arrangement.Bottom,
        ) {
            Text(
                text = movieName,
                style = Theme.textStyle.label.large,
                color = Theme.colors.onPrimaryColors.onPrimary,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
            )
            DescriptionSeparator(
                texts = listOf(mediaType, year),
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
            imageUri = R.drawable.anime_movie.toString(),
            rating = 9.9f,
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
            imageUri = R.drawable.anime_horizontal.toString(),
            rating = 9f,
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
            imageUri = R.drawable.attack_on_titan.toString(),
            rating = 8.8f,
            mediaCardType = MediaCardType.EPISODE
        )
    }

}

@PreviewLightDark
@Composable
fun PreviewMediaSliderCard() {
    AflamiTheme {
        AflamiMediaCard(
            imageUri = R.drawable.shniderlist_slider.toString(),
            rating = 8f,
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
            imageUri = R.drawable.shniderlist_slider.toString(),
            mediaCardType = MediaCardType.SLIDER,
            showRating = false,
            cardWidth = 207.dp,
            cardHeight = 276.dp,
            clickable = true,
            rating = 3.563f,
        )
    }

}