package com.feature.mediaDetails.mediaDetailsUi.ui.comon.components

import android.annotation.SuppressLint
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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.detailsImage.DetailsImage
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.tvShow.details.TvShowDetailsScreenState
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.tvShow.details.TvShowScreenInteractionListener
import com.paris.aflami.designsystem.R
import com.paris.aflami.designsystem.components.AppTopBar
import com.paris.aflami.designsystem.components.iconItemWithDefaults
import com.paris.aflami.designsystem.theme.Theme

@SuppressLint("ConfigurationScreenWidthHeight")
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun TopComponentDetails(
    state: TvShowDetailsScreenState,
    tvShowScreenInteractionListener: TvShowScreenInteractionListener,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    listState: LazyGridState,
    modifier: Modifier = Modifier
) {
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp
    val isLandscape =
        configuration.orientation == android.content.res.Configuration.ORIENTATION_LANDSCAPE
    val collapsedHeight = 56.dp
    val expandedHeight = if (isLandscape) {
        screenWidth * 0.6f
    } else {
        screenHeight * 0.4f
    }
    val maxOffsetPx = with(density) { (expandedHeight - collapsedHeight).toPx() }

    val canScroll = remember {
        derivedStateOf {
            val info = listState.layoutInfo
            info.totalItemsCount > 0 &&
                    info.visibleItemsInfo.lastOrNull()?.index != info.totalItemsCount - 1 ||
                    info.viewportEndOffset < info.totalItemsCount * info.viewportEndOffset
        }
    }

    val shrinkProgress by remember {
        derivedStateOf {
            if (!canScroll.value) {
                0f
            } else {
                when {
                    listState.firstVisibleItemIndex > 0 -> 1f
                    maxOffsetPx > 0 ->
                        (listState.firstVisibleItemScrollOffset / maxOffsetPx).coerceIn(0f, 1f)

                    else -> 0f
                }
            }
        }
    }
    val headerHeight = if (canScroll.value) {
        lerp(expandedHeight, collapsedHeight, shrinkProgress)
    } else {
        expandedHeight
    }

    with(sharedTransitionScope) {
        Box(
            modifier
                .background(Theme.colors.surface)
                .sharedBounds(
                    rememberSharedContentState(key = "Top Component Detail"),
                    animatedVisibilityScope = animatedVisibilityScope,
                    enter = fadeIn() + scaleIn(),
                    exit = fadeOut() + scaleOut(),
                    resizeMode = SharedTransitionScope.ResizeMode.RemeasureToBounds
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
            val tvShowSite = state.tvShowDetailsUiState.tvShowVideoUi.site
            val tvShowKey = state.tvShowDetailsUiState.tvShowVideoUi.key
            DetailsImage(
                imageUris = state.tvShowDetailsUiState.gallery,
                rating = state.tvShowDetailsUiState.tvShowUi.rating,
                hasVideo = !(tvShowSite.isEmpty() || tvShowKey.isEmpty()),
                onPlayClick = {
                    if (tvShowSite.isNotEmpty() && tvShowKey.isNotEmpty()) {
                        tvShowScreenInteractionListener.playYoutubeVideo(tvShowKey)
                    }
                },
                modifier = Modifier
                    .padding(bottom = 12.dp)
                    .height(headerHeight),
                nsfwThreshold = state.nsfwThreshold,
                genderThreshold = state.genderThreshold
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
                        onClick = if (state.tvShowDetailsUiState.tvShowUi.isRated.not())
                            tvShowScreenInteractionListener::onRateClick
                        else null,
                        tint = if (state.tvShowDetailsUiState.tvShowUi.isRated) Theme.colors.status.yellowAccent
                        else Theme.colors.text.title
                    ),
                ),
                modifier = Modifier
                    .background(backgroundColor)
                    .statusBarsPadding()

            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .align(Alignment.TopCenter)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = Theme.colors.gradient.overlyDark
                        )
                    )
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
    state: TvShowDetailsScreenState,
    modifier: Modifier = Modifier,
) {
    val activity = LocalActivity.current
    with(sharedTransitionScope) {

        AppTopBar(
            modifier = modifier
                .background(Theme.colors.surface)
                .statusBarsPadding()
                .sharedBounds(
                    rememberSharedContentState(key = "Top Component"),
                    animatedVisibilityScope = animatedVisibilityScope,
                    enter = fadeIn() + scaleIn(),
                    exit = fadeOut() + scaleOut(),
                    resizeMode = SharedTransitionScope.ResizeMode.RemeasureToBounds
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
                    onClick = if (state.tvShowDetailsUiState.tvShowUi.isRated.not())
                        tvShowScreenInteractionListener::onRateClick
                    else null,
                    tint = if (state.tvShowDetailsUiState.tvShowUi.isRated) Theme.colors.status.yellowAccent
                    else Theme.colors.text.title
                ),
            ),
        )
    }
}