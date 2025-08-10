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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.rememberLazyListState
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
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.tvShow.details.TvShowDetailsScreenState
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.tvShow.details.TvShowScreenInteractionListener
import com.paris_2.aflami.designsystem.R
import com.paris_2.aflami.designsystem.components.AppTopBar
import com.paris_2.aflami.designsystem.components.iconItemWithDefaults
import com.paris_2.aflami.designsystem.theme.Theme

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun TopComponentDetails(
    state: TvShowDetailsScreenState,
    tvShowScreenInteractionListener: TvShowScreenInteractionListener,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    modifier: Modifier = Modifier
) {
    with(sharedTransitionScope) {
        Box(
            modifier
                .background(Theme.colors.surface)
                .navigationBarsPadding()
                .sharedBounds(
                    rememberSharedContentState(key = "Top Component Detail"),
                    animatedVisibilityScope = animatedVisibilityScope,
                    enter = fadeIn() + scaleIn(),
                    exit = fadeOut() + scaleOut(),
                    resizeMode = SharedTransitionScope.ResizeMode.ScaleToBounds()
                )
        ) {
            val listState = rememberLazyListState()
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
            val tvShowSite = state.tvShowDetailsUiState.tvShowVideoUi.site
            val tvShowKey = state.tvShowDetailsUiState.tvShowVideoUi.key
            DetailsImage(
                imageUris = listOf(state.tvShowDetailsUiState.tvShowUi.posterUrl) + state.tvShowDetailsUiState.gallery,
                rating = state.tvShowDetailsUiState.tvShowUi.rating,
                hasVideo = !(tvShowSite.isEmpty() || tvShowKey.isEmpty()),
                onPlayClick = {
                    if (tvShowSite.isNotEmpty() && tvShowKey.isNotEmpty()) {
                        tvShowScreenInteractionListener.playYoutubeVideo(tvShowKey)
                    }
                },
                modifier = Modifier.padding(bottom = 12.dp)
            )
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
                        onClick = {
                            tvShowScreenInteractionListener.onRateClick()
                        }
                    ),
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
fun TvTopComponent(
    tvShowScreenInteractionListener: TvShowScreenInteractionListener,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    title: String,
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
                    onClick = {
                        tvShowScreenInteractionListener.onRateClick()
                    }
                ),
            ),
        )
    }
}