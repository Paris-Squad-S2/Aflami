package com.feature.mediaDetails.mediaDetailsUi.ui.comon.components


import androidx.activity.compose.LocalActivity
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.detailsImage.DetailsImage
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.movie.details.MovieDetailsScreenInteractionListener
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.movie.details.MovieDetailsScreenState
import com.paris.aflami.designsystem.R
import com.paris.aflami.designsystem.components.AppTopBar
import com.paris.aflami.designsystem.components.PageLoadingPlaceHolder
import com.paris.aflami.designsystem.components.iconItemWithDefaults
import com.paris.aflami.designsystem.theme.Theme

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MovieTopComponentDetails(
    state: MovieDetailsScreenState,
    movieDetailsScreenInteractionListener: MovieDetailsScreenInteractionListener,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    listState: LazyGridState,
    modifier: Modifier = Modifier
) {
    with(sharedTransitionScope) {
        Box(

            modifier
                .background(Theme.colors.surface)
                .sharedBounds(
                    rememberSharedContentState(key = "Top Component Detail"),
                    animatedVisibilityScope = animatedVisibilityScope,
                    enter = fadeIn() + scaleIn(),
                    exit = fadeOut() + scaleOut(),
                    resizeMode = SharedTransitionScope.ResizeMode.ScaleToBounds()
                )
        ) {
            val density = LocalDensity.current
            val maxScrollPx = with(density) { 56.dp.toPx() }
            val alpha by remember {
                derivedStateOf {
                    val scroll =
                        if (listState.firstVisibleItemIndex > 0) maxScrollPx else listState.firstVisibleItemScrollOffset.toFloat()
                    (scroll / maxScrollPx).coerceIn(0f, 1f)
                }
            }
            val backgroundColor = Theme.colors.surface.copy(alpha = alpha)
            val activity = LocalActivity.current
            val site = state.movieDetailsUiState.movieVideoUi.site
            val key = state.movieDetailsUiState.movieVideoUi.key
            if (state.isImageLoading) {
                PageLoadingPlaceHolder(
                    modifier = Modifier.padding(bottom = 12.dp)
                )
            } else {
                DetailsImage(
                    imageUris = listOf(state.movieDetailsUiState.movie.posterUrl) + state.movieDetailsUiState.gallery,
                    rating = state.movieDetailsUiState.movie.rating,
                    onPlayClick = {
                        if (site.isNotEmpty() && key.isNotEmpty()) {
                            movieDetailsScreenInteractionListener.playYoutubeVideo(key)
                        }
                    },
                    hasVideo = !(site.isEmpty() || key.isEmpty()),
                    modifier = Modifier.padding(bottom = 12.dp),
                    nsfwThreshold = state.nsfwThreshold,
                    genderThreshold = state.genderThreshold
                )
            }
            AppTopBar(
                leadingIcons = listOf(
                    iconItemWithDefaults(
                        icon = ImageVector.vectorResource(R.drawable.ic_back),
                        onClick = { activity?.finish() }
                    )
                ),
                trailingIcons = listOf(
                    iconItemWithDefaults(
                        icon = ImageVector.vectorResource(R.drawable.ic_star),
                        onClick = if (state.movieDetailsUiState.movie.isRated.not())
                            movieDetailsScreenInteractionListener::onRateClick
                        else null,
                        tint = if (state.movieDetailsUiState.movie.isRated) Theme.colors.status.yellowAccent
                        else Theme.colors.text.title
                    ),
                    iconItemWithDefaults(
                        icon = ImageVector.vectorResource(R.drawable.ic_heart_add),
                        onClick = if (state.movieDetailsUiState.movie.isAddedToLists.not())
                            movieDetailsScreenInteractionListener::onAddToListClick
                        else null,
                        tint = if (state.movieDetailsUiState.movie.isAddedToLists) Theme.colors.status.yellowAccent
                        else Theme.colors.text.title

                    )
                ),
                modifier = Modifier
                    .statusBarsPadding()
                    .background(backgroundColor)

            )
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MovieTopComponent(
    movieDetailsScreenInteractionListener: MovieDetailsScreenInteractionListener,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    title: String,
    state: MovieDetailsScreenState,
    modifier: Modifier = Modifier,
) {
    val activity = LocalActivity.current
    with(sharedTransitionScope) {
        AppTopBar(
            modifier = modifier
                .statusBarsPadding()
                .background(Theme.colors.stroke)
                .padding(bottom = 1.dp)
                .background(Theme.colors.surface)
                .sharedBounds(
                    rememberSharedContentState(key = "Top Component"),
                    animatedVisibilityScope = animatedVisibilityScope,
                    enter = fadeIn() + scaleIn(),
                    exit = fadeOut() + scaleOut(),
                    resizeMode = SharedTransitionScope.ResizeMode.ScaleToBounds()
                ),
            title = title,
            leadingIcons = listOf(
                iconItemWithDefaults(
                    icon = ImageVector.vectorResource(R.drawable.ic_back),
                    onClick = { activity?.finish() }
                )
            ),
            trailingIcons = listOf(
                iconItemWithDefaults(
                    icon = ImageVector.vectorResource(R.drawable.ic_star),
                    onClick = if (state.movieDetailsUiState.movie.isRated.not())
                        movieDetailsScreenInteractionListener::onRateClick
                    else null,
                    tint = if (state.movieDetailsUiState.movie.isRated) Theme.colors.status.yellowAccent
                    else Theme.colors.text.title
                ),
                iconItemWithDefaults(
                    icon = ImageVector.vectorResource(R.drawable.ic_heart_add),
                    onClick = if (state.movieDetailsUiState.movie.isAddedToLists.not())
                        movieDetailsScreenInteractionListener::onAddToListClick
                    else null,
                    tint = if (state.movieDetailsUiState.movie.isAddedToLists) Theme.colors.status.yellowAccent
                    else Theme.colors.text.title

                )
            ),
        )
    }
}