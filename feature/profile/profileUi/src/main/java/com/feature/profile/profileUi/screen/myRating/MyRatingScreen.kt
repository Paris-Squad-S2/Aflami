package com.feature.profile.profileUi.screen.myRating

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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.feature.profile.profileUi.R
import com.feature.profile.profileUi.screen.components.MediaCard
import com.feature.profile.profileUi.screen.components.MediaCardType
import com.feature.profile.profileUi.screen.watchHistory.MediaTypeUi
import com.feature.profile.profileUi.screen.watchHistory.MediaUiState
import com.paris_2.aflami.designsystem.components.AppTopBar
import com.paris_2.aflami.designsystem.components.NetworkError
import com.paris_2.aflami.designsystem.components.PageLoadingPlaceHolder
import com.paris_2.aflami.designsystem.components.TabRow
import com.paris_2.aflami.designsystem.components.iconItemWithDefaults
import com.paris_2.aflami.designsystem.theme.Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyRatingScreen(
    viewModel: MyRatingViewModel = hiltViewModel(),
) {
    val state = viewModel.screenState.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 12.dp)
    ) {
        var selectedIndex by remember { mutableIntStateOf(0) }
        AppTopBar(
            logo = iconItemWithDefaults(
                icon = ImageVector.vectorResource(com.paris_2.aflami.designsystem.R.drawable.ic_back),
                onClick = viewModel::onBackClick,
                backgroundColor = Theme.colors.surface,
                tint = Theme.colors.text.title
            ),
            title = stringResource(R.string.my_rating),
        )
        TabRow(
            tabItems = listOf(
                stringResource(R.string.movies),
                stringResource(R.string.tv_shows)
            ),
            selectedIndex = selectedIndex,
            onTabSelected = { tabIndex ->
                selectedIndex = tabIndex
                viewModel.onTabSelected(
                    if (tabIndex == 0) MediaTypeUi.MOVIE else MediaTypeUi.TVSHOW
                )
            }
        )
        if (state.value.myRatingMedia.isNotEmpty()) {
            MyRatingScreenContent(
                myRatingList = state.value.myRatingMedia,
                onMediaCardClick = viewModel::onMediaCardClick,
                onFavouriteIconClick = viewModel::onFavouriteIconClick
            )
        } else if (state.value.isLoading) {
            PageLoadingPlaceHolder(
                modifier = Modifier.fillMaxSize()
            )
        }else if (state.value.myRatingMedia.isEmpty()) {
            EmptyRatingContent(
                isMovie = selectedIndex == 0
            )
        } else if (state.value.errorMessage != null) {
            NetworkError(
                modifier = Modifier.fillMaxSize(), onRetry = viewModel::onRetry
            )
        }
    }
}

@Composable
fun MyRatingScreenContent(
    myRatingList: List<MediaUiState>,
    onMediaCardClick: (MediaUiState) -> Unit,
    onFavouriteIconClick: (MediaUiState) -> Unit,
) {
    val lazyGridState = rememberLazyGridState()
    val isScrolling by remember { derivedStateOf { lazyGridState.isScrollInProgress } }
    LazyVerticalGrid(
        state = lazyGridState,
        columns = GridCells.Adaptive(minSize = 160.dp),
        contentPadding = PaddingValues(start = 12.dp, end = 12.dp),
    ) {
        items(myRatingList) { media ->
            MediaCard(
                modifier = Modifier
                    .padding(6.dp)
                    .clickable {
                        onMediaCardClick(media)
                    },
                imageUri = media.imageUri,
                rating = media.rating?.toFloat(),
                movieName = media.title,
                mediaType = media.type.mediaName,
                year = media.yearOfRelease.year.toString(),
                mediaCardType = MediaCardType.NORMAL,
                showGradientFilter = true,
                enabled = !isScrolling,
                isRated = true,
                onFavouriteIconClick = {onFavouriteIconClick(media)}
            )
        }
    }
}