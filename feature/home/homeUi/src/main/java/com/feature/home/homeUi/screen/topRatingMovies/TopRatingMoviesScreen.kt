package com.feature.home.homeUi.screen.topRatingMovies

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.feature.home.homeUi.screen.home.MediaUiState
import com.paris_2.aflami.designsystem.components.AflamiMediaCard
import com.paris_2.aflami.designsystem.components.AflamiSectionTitle
import com.paris_2.aflami.designsystem.components.MediaCardType
import com.paris_2.aflami.designsystem.components.NetworkError
import com.paris_2.aflami.designsystem.components.PageLoadingPlaceHolder
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun TopRatingMoviesScreen(
    viewModel: TopRatingMoviesViewModel = koinViewModel(),
) {
    val state = viewModel.screenState.collectAsState()

    Column {
        AflamiSectionTitle(
            title = "Top Rating",
            modifier = Modifier.padding( top = 23.dp)
        )
        if (state.value.topRatingMovies.isNotEmpty()) {
            TopRatingMoviesContent(
                continueWatchingList = state.value.topRatingMovies,
                onMediaCardClick = viewModel::onMediaCardClick
            )
        } else if (state.value.isLoading) {
            PageLoadingPlaceHolder(
                modifier = Modifier.fillMaxSize()
            )
        } else if (state.value.errorMessage != null) {
            NetworkError(
                modifier = Modifier.fillMaxSize(),
                onRetry = viewModel::onRetry
            )
        }
    }
}

@Composable
fun TopRatingMoviesContent(
    continueWatchingList: List<MediaUiState>,
    onMediaCardClick: (MediaUiState) -> Unit,
) {
    val lazyGridState = rememberLazyGridState()
    val isScrolling by remember { derivedStateOf { lazyGridState.isScrollInProgress } }

    LazyVerticalGrid(
        state = lazyGridState,
        columns = GridCells.Adaptive(minSize = 160.dp),
        contentPadding = PaddingValues(start = 8.dp, end = 8.dp, bottom = 8.dp, top = 8.dp),
    ) {


        items(continueWatchingList) { media ->
            AflamiMediaCard(
                modifier = Modifier
                    .padding(8.dp)
                    .clickable {
                        onMediaCardClick(media)
                    },
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