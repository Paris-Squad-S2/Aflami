package com.feature.home.homeUi.screen.topRatingMovies

import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.feature.home.homeUi.R
import com.feature.home.homeUi.screen.home.MediaUiState
import com.paris_2.aflami.designsystem.components.Icon
import com.paris_2.aflami.designsystem.components.MediaCard
import com.paris_2.aflami.designsystem.components.MediaCardType
import com.paris_2.aflami.designsystem.components.NetworkError
import com.paris_2.aflami.designsystem.components.PageLoadingPlaceHolder
import com.paris_2.aflami.designsystem.components.TopAppBar
import com.paris_2.aflami.designsystem.components.iconItemWithDefaults
import com.paris_2.aflami.designsystem.theme.Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopRatingMoviesScreen(
    viewModel: TopRatingMoviesViewModel = hiltViewModel(),
) {
    val state = viewModel.screenState.collectAsState()
    val context = LocalActivity.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = Theme.colors.gradient.pointsOverly + listOf(
                        Theme.colors.surface.copy(alpha = 0.5f), Theme.colors.surface
                    )
                )
            )
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.ic_fire),
            contentDescription = "",
            tint = Theme.colors.onPrimaryColors.onPrimary,
            modifier = Modifier
                .width(50.dp)
                .height(58.dp)
                .align(Alignment.TopEnd)
                .offset(x = (0).dp, y = 84.dp)
                .graphicsLayer {
                    alpha = 0.12f
                }
                .blur(radius = 4.dp)

        )
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.ic_fire),
            contentDescription = "",
            tint = Theme.colors.onPrimaryColors.onPrimary,
            modifier = Modifier
                .width(26.dp)
                .height(30.dp)
                .align(Alignment.TopEnd)
                .offset(x = (-39).dp, y = 62.dp)
                .graphicsLayer {
                    alpha = 0.1f
                }
                .blur(radius = 3.dp))
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.ic_fire),
            contentDescription = "",
            tint = Theme.colors.onPrimaryColors.onPrimary,
            modifier = Modifier
                .width(20.dp)
                .height(22.dp)
                .align(Alignment.TopEnd)
                .offset(x = (-64).dp, y = 46.dp)
                .graphicsLayer {
                    alpha = 0.08f
                }
                .blur(radius = 2.dp))
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.ic_fire),
            contentDescription = "",
            tint = Theme.colors.onPrimaryColors.onPrimary,
            modifier = Modifier
                .width(156.dp)
                .height(180.dp)
                .align(Alignment.Center)
                .offset(y = (-90).dp)
                .graphicsLayer {
                    alpha = 0.06f
                }
                .blur(radius = 3.dp)

        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 12.dp)
        ) {

            TopAppBar(
                logo = iconItemWithDefaults(
                    icon = ImageVector.vectorResource(com.paris_2.aflami.designsystem.R.drawable.ic_back),
                    onClick = {
                        context?.finish()
                    },
                    backgroundColor = Theme.colors.surfaceHigh,
                    tint = Theme.colors.text.title,
                ),
                title = stringResource(R.string.top_rating),
                modifier = Modifier.padding(top = 23.dp, bottom = 0.dp)
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
                    modifier = Modifier.fillMaxSize(), onRetry = viewModel::onRetry
                )
            }
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
            )
        }
    }
}