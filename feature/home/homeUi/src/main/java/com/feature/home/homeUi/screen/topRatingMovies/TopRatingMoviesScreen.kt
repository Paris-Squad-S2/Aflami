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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.feature.home.homeUi.R
import com.feature.home.homeUi.screen.home.MediaUiState
import com.paris_2.aflami.designsystem.components.AflamiMediaCard
import com.paris_2.aflami.designsystem.components.MediaCardType
import com.paris_2.aflami.designsystem.components.NetworkError
import com.paris_2.aflami.designsystem.components.PageLoadingPlaceHolder
import com.paris_2.aflami.designsystem.components.TopAppBar
import com.paris_2.aflami.designsystem.components.iconItemWithDefaults
import com.paris_2.aflami.designsystem.theme.Theme
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopRatingMoviesScreen(
    viewModel: TopRatingMoviesViewModel = koinViewModel(),
) {
    val state = viewModel.screenState.collectAsState()

    Column {
        TopAppBar(
            logo = iconItemWithDefaults(
                icon = ImageVector.vectorResource(com.paris_2.aflami.designsystem.R.drawable.ic_back),
                onClick = viewModel::onBackButtonClick
                ,
                backgroundColor = Theme.colors.primaryVariant,
            ),
            title = stringResource(R.string.top_rating),
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
        contentPadding = PaddingValues(start = 12.dp, end = 12.dp),
    ) {


        items(continueWatchingList) { media ->
            AflamiMediaCard(
                modifier = Modifier
                    .padding(6.dp)
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