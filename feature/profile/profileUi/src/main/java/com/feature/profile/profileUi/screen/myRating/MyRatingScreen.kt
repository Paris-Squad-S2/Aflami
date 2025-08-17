package com.feature.profile.profileUi.screen.myRating

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
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
import com.paris_2.aflami.designsystem.components.PlaceholderView
import com.paris_2.aflami.designsystem.components.TabRow
import com.paris_2.aflami.designsystem.components.iconItemWithDefaults
import com.paris_2.aflami.designsystem.theme.Theme

@Composable
fun MyRatingScreen(
    viewModel: MyRatingViewModel = hiltViewModel(),
) {
    val state = viewModel.screenState.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
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
                if (tabIndex != selectedIndex) {
                    selectedIndex = tabIndex
                    viewModel.onTabSelected(
                        if (tabIndex == 0) MediaTypeUi.MOVIE else MediaTypeUi.TVSHOW
                    )
                }
            }
        )
        if (state.value.isLoading) {
            PageLoadingPlaceHolder(
                modifier = Modifier.fillMaxSize()
            )
        } else if (state.value.myRatingMedia.isNotEmpty()) {
            MyRatingScreenContent(
                myRatingList = state.value.myRatingMedia,
                onMediaCardClick = viewModel::onMediaCardClick,
                onFavouriteIconClick = viewModel::onFavouriteIconClick,
                nsfwThreshold = state.value.nsfwThreshold,
                genderThreshold = state.value.genderThreshold
            )
        } else if (state.value.myRatingMedia.isEmpty() && state.value.errorMessage == null) {
            PlaceholderView(
                modifier = Modifier.fillMaxSize(),
                image = painterResource(R.drawable.empty_rating),
                title = if (selectedIndex == 0) stringResource(R.string.no_rated_movies_yet) else stringResource(R.string.no_rated_tv_yet),
                subTitle = if (selectedIndex == 0) stringResource(R.string.open_a_movie) else stringResource(R.string.open_a_tv_show),
                spacer = 24.dp,
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
    nsfwThreshold: Float = 0.8f,
    genderThreshold: Float = 0.6f,
) {
    val lazyGridState = rememberLazyGridState()
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
                isRated = true,
                onFavouriteIconClick = {onFavouriteIconClick(media)},
                nsfwThreshold = nsfwThreshold,
                genderThreshold = genderThreshold
            )
        }
    }
}