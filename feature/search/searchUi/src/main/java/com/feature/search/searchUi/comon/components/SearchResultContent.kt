package com.feature.search.searchUi.comon.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.domain.search.model.Media
import com.paris_2.aflami.designsystem.components.AflamiMediaCard
import com.paris_2.aflami.designsystem.components.MediaCardType

@Composable
fun SearchResultContent(
    searchResult: List<Media>,
    onMediaCardClick: (Int) -> Unit,
) {
    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        columns = GridCells.Adaptive(minSize = 160.dp),
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(
            items = searchResult,
            key = { media -> media.id },
            contentType = { "media_card" }
        ) { media ->
            AflamiMediaCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onMediaCardClick(media.id)
                    },
                imageUri = media.imageUri,
                rating = media.rating.toString(),
                movieName = media.title,
                mediaType = media.type.displayName,
                year = media.yearOfRelease.year.toString(),
                mediaCardType = MediaCardType.NORMAL,
                showGradientFilter = true,
            )

        }
    }
}