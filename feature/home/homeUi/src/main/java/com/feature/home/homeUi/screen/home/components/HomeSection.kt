package com.feature.home.homeUi.screen.home.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.feature.home.homeUi.screen.home.MediaTypeUi
import com.feature.home.homeUi.screen.home.MediaUiState
import com.feature.home.homeUi.utils.shimmerable
import com.paris.aflami.designsystem.components.AppIcon
import com.paris.aflami.designsystem.theme.AflamiTheme

@Composable
fun HomeSection(
    title: String,
    mediaList: List<MediaUiState>,
    iconColor: Color? = null,
    onMediaClick: (media: MediaUiState) -> Unit = {},
    onSectionAllClick: () -> Unit = {},
    leadingIconPainter: ImageVector? = null,
    isScrolling: Boolean,
    isShimmerEnabled: Boolean,
    modifier: Modifier,
    nsfwThreshold: Float = 0.8f,
    genderThreshold: Float = 0.6f
) {
    Column(
        modifier = modifier
    ) {
        AnimatedVisibility(
            visible = !isShimmerEnabled&&mediaList.isNotEmpty(),
            enter = fadeIn(),
            exit = fadeOut()
        ){
        SectionTitle(
            title = title,
            hasViewAll = mediaList.size>=10,
            icon = {
                leadingIconPainter?.let {
                    AppIcon(
                        imageVector = it,
                        contentDescription = "$title icon",
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .size(width = 16.dp, height = 18.dp),
                        tint = iconColor ?: Color.Unspecified,
                    )
                }
            },
            onClickViewAll = onSectionAllClick,
            shimmerModifier = Modifier.shimmerable(enabled = isShimmerEnabled&&mediaList.isEmpty())
        )}

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {

                items(3) {
                    AnimatedVisibility(
                        visible = isShimmerEnabled&&mediaList.isEmpty(),
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        Box(
                            modifier = Modifier
                                .width(156.dp)
                                .height(222.dp)
                                .padding(end = 8.dp)
                                .shimmerable(enabled = true)
                        )
                    }
                }

            items(mediaList) { media ->
                MediaCard(
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .clickable { onMediaClick(media) }
                    ,
                    imageUri = media.imageUri,
                    rating = media.rating?.toFloat(),
                    movieName = media.title,
                    mediaType = stringResource(media.type.mediaID),
                    year = media.yearOfRelease.year.toString(),
                    mediaCardType = MediaCardType.NORMAL,
                    showGradientFilter = true,
                    enabled = !isScrolling,
                    nsfwThreshold = nsfwThreshold,
                    genderThreshold = genderThreshold
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewHomeSectionLoaded() {
    val sampleMediaList = List(5) { index ->
        MediaUiState(
            id = index,
            imageUri = "https://via.placeholder.com/156x222.png?text=Movie+$index",
            title = "Movie $index",
            type = MediaTypeUi.MOVIE,
            categories = emptyList(),
            yearOfRelease = kotlinx.datetime.LocalDate(2024, 5, 1),
            rating = 8.5
        )
    }

    AflamiTheme {
        HomeSection(
            title = "Popular Movies",
            mediaList = sampleMediaList,
            isScrolling = false,
            isShimmerEnabled = false,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview
@Composable
fun PreviewHomeSectionShimmer() {
    AflamiTheme {
        HomeSection(
            title = "Popular Movies",
            mediaList = emptyList(),
            isScrolling = false,
            isShimmerEnabled = true,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview
@Composable
fun PreviewHomeSectionEmpty() {
    AflamiTheme {
        HomeSection(
            title = "Popular Movies",
            mediaList = emptyList(),
            isScrolling = false,
            isShimmerEnabled = false,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

