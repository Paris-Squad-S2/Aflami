package com.feature.home.homeUi.screen.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.feature.home.homeUi.screen.home.MediaUiState
import com.paris_2.aflami.designsystem.components.Icon
import com.paris_2.aflami.designsystem.components.MediaCard
import com.paris_2.aflami.designsystem.components.MediaCardType
import com.paris_2.aflami.designsystem.components.SectionTitle

@Composable
fun HomeSection(
    title: String,
    mediaList: List<MediaUiState>,
    iconColor: Color? = null,
    onMediaClick: (media: MediaUiState) -> Unit = {},
    onSectionAllClick: () -> Unit = {},
    leadingIconPainter: ImageVector? = null,
    isScrolling: Boolean,
    modifier: Modifier,
) {
    Column(
        modifier = modifier
    ) {
        SectionTitle(
            title = title,
            hasViewAll = true,
            icon = {
                leadingIconPainter?.let { it->   Icon(
                    imageVector = it,
                    contentDescription = "$title icon",
                    modifier = Modifier.padding(start = 8.dp),
                    tint = iconColor ?: Color.Unspecified,
                )
            }},
            onClickViewAll = onSectionAllClick,
            modifier = Modifier.padding(top = 6.dp)
        )

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(mediaList) { media ->
                MediaCard(
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .clickable { onMediaClick(media) },
                    imageUri = media.imageUri,
                    rating = media.rating.toFloat(),
                    movieName = media.title,
                    mediaType = media.type.mediaName,
                    year = media.yearOfRelease.year.toString(),
                    mediaCardType = MediaCardType.NORMAL,
                    showGradientFilter = true,
                    enabled = !isScrolling,
                )
            }
        }
    }
}
