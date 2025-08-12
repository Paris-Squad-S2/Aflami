package com.feature.lists.listsUi.screens.listDetails.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.designSystem.safeimageviewer.SafeImageViewer
import com.paris_2.aflami.designsystem.R
import com.paris_2.aflami.designsystem.components.AppIcon
import com.paris_2.aflami.designsystem.components.AppText
import com.paris_2.aflami.designsystem.theme.AflamiTheme
import com.paris_2.aflami.designsystem.theme.Theme

@Composable
fun MediaCard(
    modifier: Modifier = Modifier,
    imageUri: String,
    rating: Float?,
    movieName: String = "",
    year: String = "",
    onRemoveClick: () -> Unit,
    showRating: Boolean = true,
    showGradientFilter: Boolean = false,
    nsfwThreshold: Float = 0.8f,
    genderThreshold: Float = 0.6f,
    enabled: Boolean = true
) {

    Box(
        modifier = modifier
            .width(156.dp)
            .height(222.dp)
            .border(
                width = 1.dp,
                color = Color.White.copy(alpha = 0.08f),
                shape = RoundedCornerShape(16.dp)
            )
            .clip(RoundedCornerShape(16.dp))
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
            errorContent = {
                Image(
                    painter = painterResource(id = R.drawable.img_disconnect),
                    contentDescription = null,
                    modifier = Modifier.size(48.dp).align(Alignment.Center),
                    contentScale = ContentScale.Crop
                )
            }
        )
        Row(
            modifier = Modifier.align(Alignment.TopCenter).fillMaxWidth().padding(4.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier.size(32.dp)
                    .background( Theme.colors.iconBackground,shape = RoundedCornerShape(12.dp))
                    .clickable{onRemoveClick()}
            ){
                AppIcon(
                    imageVector = ImageVector.vectorResource(com.feature.lists.listsUi.R.drawable.ic_heart_remove),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier.size(20.dp).align(Alignment.Center)
                )
            }

            AnimatedVisibility(
                showRating && rating != null,
            ) {
                RatingCard(
                    rating = rating,
                )
            }
        }

        if (showGradientFilter) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(108.dp)
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
                    top = 174.dp,
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
                texts = listOf(year),
                textColor = Theme.colors.onPrimaryColors.onPrimaryBody
            )

        }
    }
}

@PreviewLightDark
@Composable
fun PreviewMediaNormalCard() {
    AflamiTheme {
        MediaCard(
            imageUri = R.drawable.anime_movie.toString(),
            rating = 9.9f,
            movieName = "Your Name",
            year = "2016",
            onRemoveClick = {},
            showRating = true,
            showGradientFilter = true
        )
    }

}
