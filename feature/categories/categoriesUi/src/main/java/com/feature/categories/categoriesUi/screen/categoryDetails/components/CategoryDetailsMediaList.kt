package com.feature.categories.categoriesUi.screen.categoryDetails.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.feature.categories.categoriesUi.R
import com.feature.categories.categoriesUi.screen.categoryDetails.MediaUI
import com.paris_2.domain.media.entity.Media
import com.paris_2.domain.media.entity.MediaType

@Composable
fun CategoryDetailsMediaList(mediaList: List<MediaUI>, onMediaSelected: (MediaUI) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 200.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(mediaList) { media ->
            MediaCard(
                modifier = Modifier
                    .fillMaxWidth(),
                imageUri = media.posterPath,
                rating = media.voteAverage?.toFloat(),
                movieName = media.title,
                mediaType = stringResource(
                    when (media.type) {
                        MediaType.Movie -> R.string.movie
                        MediaType.TvShow -> R.string.tv_show
                    }
                ),
                year = media.releaseDate.take(4),
                showGradientFilter = true,
                onClick = { onMediaSelected(media) },
            )
        }
    }
}