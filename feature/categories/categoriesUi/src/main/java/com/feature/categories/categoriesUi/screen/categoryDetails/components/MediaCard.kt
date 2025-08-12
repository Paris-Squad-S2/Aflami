package com.feature.categories.categoriesUi.screen.categoryDetails.components

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
    movieName: String,
    mediaType: String,
    year: String,
    onClick: (() -> Unit)? = null,
    showGradientFilter: Boolean,
    cardHeight: Dp? = null,
) {
    val finalCardHeight = cardHeight ?: 196.dp
    val clipRadius = 16.dp

    Box(
        modifier = modifier
            .height(finalCardHeight)
            .border(
                width = 1.dp,
                color = Color.White.copy(alpha = 0.08f),
                shape = RoundedCornerShape(clipRadius)
            )
            .clip(RoundedCornerShape(clipRadius))
            .clickable(enabled = onClick != null, onClick = onClick ?: {})
    ) {
        SafeImageViewer(
            imageUrl = imageUri,
            modifier = Modifier.fillMaxSize(),
            contentDescription = "media poster",
            contentScale = ContentScale.Crop,
            loadingContent = {
                Image(
                    painter = painterResource(id = R.drawable.ic_film_roll),
                    contentDescription = null,
                    modifier = Modifier
                        .size(48.dp)
                        .align(Alignment.Center),
                    contentScale = ContentScale.Crop
                )
            },
            errorContent = { errorMessage ->
                Image(
                    painter = painterResource(id = R.drawable.img_disconnect),
                    contentDescription = null,
                    modifier = Modifier
                        .size(48.dp)
                        .align(Alignment.Center),
                    contentScale = ContentScale.Crop
                )
            }
        )

        RatingCard(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 8.dp, end = 8.dp),
            rating = rating,
        )

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
    }
}


@PreviewLightDark
@Composable
fun PreviewMediaCard() {
    AflamiTheme {
        MediaCard(
            imageUri = R.drawable.anime_horizontal.toString(),
            rating = 9f,
            movieName = "Grave of the Fireflies",
            mediaType = "TV show",
            year = "2016",
            showGradientFilter = true,
            onClick = {},
        )
    }

}
