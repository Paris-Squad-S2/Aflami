package com.feature.search.searchUi.comon.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.feature.search.searchUi.screen.search.MediaUiState
import com.paris_2.aflami.designsystem.components.AflamiMediaCard
import com.paris_2.aflami.designsystem.components.MediaCardType

@Composable
fun SearchResultContent(
    searchResult: List<MediaUiState>,
    onMediaCardClick: (Int) -> Unit,
) {
    val lazyGridState = rememberLazyGridState()
    val isScrolling by remember { derivedStateOf { lazyGridState.isScrollInProgress } }

    LazyVerticalGrid(
        state = lazyGridState,
        columns = GridCells.Adaptive(minSize = 160.dp),
        contentPadding = PaddingValues(start = 8.dp, end = 8.dp, bottom = 8.dp, top = 8.dp),
    ) {
        items(
            items = searchResult,
            key = { media -> media.id },
            contentType = { "media_card" }
        ) { media ->
            AflamiMediaCard(
                modifier = Modifier
                    .padding(8.dp)
                    .clickable {
                        onMediaCardClick(media.id)
                    },
                imageUri = media.imageUri,
                rating = media.rating.toString(),
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