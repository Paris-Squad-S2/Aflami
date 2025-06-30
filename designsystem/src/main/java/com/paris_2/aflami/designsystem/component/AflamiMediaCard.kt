package com.paris_2.aflami.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.paris_2.aflami.designsystem.R
import com.paris_2.aflami.designsystem.theme.AflamiTheme
import com.paris_2.aflami.designsystem.theme.Theme

@Composable
fun AflamiMediaCard(
    modifier: Modifier = Modifier,
    imageId: Int,
    rating: String,
    movieName: String = "",
    mediaType: String = "",
    year: String = "",
    onClick: () -> Unit = {},
    mediaCardType: MediaCardType
) {
    val (cardWidth, cardHeight) = when (mediaCardType) {
        MediaCardType.UP_COMING -> 328.dp to 196.dp
        MediaCardType.NORMAL -> 156.dp to 222.dp
        MediaCardType.EPISODE -> 116.dp to 78.dp
    }
    val clipRadius = if (mediaCardType == MediaCardType.EPISODE) 12.dp else 16.dp
    val ratingCardPadding = if (mediaCardType == MediaCardType.EPISODE) 73.dp else cardWidth - 54.dp
    Box(
        modifier = modifier
            .width(cardWidth)
            .height(cardHeight)
            .border(
                width = 1.dp,
                color = Color.White.copy(alpha = 0.08f),
                shape = RoundedCornerShape(clipRadius)
            )
            .clip(RoundedCornerShape(clipRadius))
            .clickable { onClick() }
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(imageId),
            contentDescription = "media poster",
            contentScale = ContentScale.Crop
        )

        RatingCard(
            rating = rating,
            modifier = Modifier.padding(start = ratingCardPadding, top = 4.dp, end = 4.dp)
        )


        if (mediaCardType != MediaCardType.EPISODE) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(cardHeight - 114.dp)
                    .align(Alignment.BottomCenter)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = Theme.colors.gradient.overly.asReversed()
                        )
                    )
            )
            Column(
                modifier = Modifier.padding(top = cardHeight - 48.dp, start = 8.dp)
            ) {
                Text(
                    text = movieName,
                    style = Theme.textStyle.label.large,
                    color = Theme.colors.onPrimaryColors.onPrimary,
                )
                DescriptionSeparator(firstText = mediaType, secondText = year)

            }
        }
    }
}

enum class MediaCardType {
    NORMAL,
    UP_COMING,
    EPISODE
}

@PreviewLightDark
@Composable
fun PreviewMediaNormalCard() {
    AflamiTheme {
        AflamiMediaCard(
            imageId = R.drawable.anime_movie,
            rating = 9.9.toString(),
            movieName = "Your Name",
            mediaType = "TV show",
            year = "2016",
            mediaCardType = MediaCardType.NORMAL
        )
    }

}

@PreviewLightDark
@Composable
fun PreviewMediaUpComingCard() {
    AflamiTheme {
        AflamiMediaCard(
            imageId = R.drawable.anime_horizontal,
            rating = 9.9.toString(),
            movieName = "Grave of the Fireflies",
            mediaType = "TV show",
            year = "2016",
            mediaCardType = MediaCardType.UP_COMING
        )
    }

}

@PreviewLightDark
@Composable
fun PreviewMediaEpisodeCard() {
    AflamiTheme {
        AflamiMediaCard(
            imageId = R.drawable.attack_on_titan,
            rating = 8.toString(),
            mediaCardType = MediaCardType.EPISODE
        )
    }

}