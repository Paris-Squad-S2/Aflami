package com.feature.mediaDetails.mediaDetailsUi.ui.comon.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.paris_2.aflami.designsystem.components.AppText
import com.paris_2.aflami.designsystem.theme.AflamiTheme
import com.paris_2.aflami.designsystem.theme.Theme

@Composable
fun MediaCard(
    modifier: Modifier = Modifier,
    imageUri: String,
    rating: Float?,
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
    nsfwThreshold: Float = 0.8f,
    genderThreshold: Float = 0.6f,
    onPlayButtonClick: () -> Unit = {},
    enabled: Boolean = true
) {
    val (finalCardWidth, finalCardHeight) = when (mediaCardType) {
        MediaCardType.UP_COMING -> (cardWidth ?: 328.dp) to (cardHeight ?: 196.dp)
        MediaCardType.EPISODE -> (cardWidth ?: 116.dp) to (cardHeight ?: 78.dp)
    }
    val clipRadius = when (mediaCardType) {
        MediaCardType.EPISODE -> 12.dp
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
            imageUrl = imageUri,
            modifier = Modifier.fillMaxSize(),
            contentDescription = "media poster",
            contentScale = ContentScale.Crop,
            nsfwThreshold = nsfwThreshold,
            genderThreshold = genderThreshold,
            loadingContent = {
                Image(
                    painter = painterResource(id = R.drawable.ic_film_roll),
                    contentDescription = null,
                    modifier = Modifier.size(48.dp).align(Alignment.Center),
                    contentScale = ContentScale.Crop
                )
            },
            errorContent = { errorMessage ->
                Image(
                    painter = painterResource(id = R.drawable.img_disconnect),
                    contentDescription = null,
                    modifier = Modifier.size(48.dp).align(Alignment.Center),
                    contentScale = ContentScale.Crop
                )
            }
        )

        AnimatedVisibility(
            showRating && rating != null,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 8.dp, end = 8.dp)
        ) {
            RatingCard(
                rating = rating,
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
            AppText(
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
    UP_COMING,
    EPISODE,
}

@PreviewLightDark
@Composable
fun PreviewMediaUpComingCard() {
    AflamiTheme {
        MediaCard(
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
        MediaCard(
            imageUri = R.drawable.attack_on_titan.toString(),
            rating = 8.8f,
            mediaCardType = MediaCardType.EPISODE
        )
    }

}