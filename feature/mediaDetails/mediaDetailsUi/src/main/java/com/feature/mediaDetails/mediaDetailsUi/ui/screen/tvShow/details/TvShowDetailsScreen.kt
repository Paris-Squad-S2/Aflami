package com.feature.mediaDetails.mediaDetailsUi.ui.screen.tvShow.details

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.feature.mediaDetails.mediaDetailsUi.R
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.ChipsRowSection
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.EpisodeCard
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.MediaCard
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.MediaCardType
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.RatingDialog
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.TopComponentDetails
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.TvTopComponent
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.VideoPlayer
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.castSection.CastSection
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.companyProductionSection.productionCompanySection
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.descriptionSection.DescriptionSection
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.gallerySection
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.reviewSection.ReviewsSection
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.seasonSection.SeasonHeader
import com.paris.aflami.designsystem.components.AppSnackBar
import com.paris.aflami.designsystem.components.AppText
import com.paris.aflami.designsystem.components.PageLoadingPlaceHolder
import com.paris.aflami.designsystem.theme.Theme
import com.feature.mediaDetails.mediaDetailsUi.R as featureMediaDetailsUiR
import com.paris.aflami.designsystem.R as designsystemR

@Composable
fun TvShowDetailsScreen(viewModel: TvShowDetailsViewModel = hiltViewModel()) {
    val state = viewModel.screenState.collectAsStateWithLifecycle()

    TvShowDetailsScreenContent(
        state = state.value,
        tvShowScreenInteractionListener = viewModel
    )
}

@SuppressLint("ConfigurationScreenWidthHeight")
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun TvShowDetailsScreenContent(
    state: TvShowDetailsScreenState,
    tvShowScreenInteractionListener: TvShowScreenInteractionListener,
) {
    val tvChips = TvShowChips.entries
    var currentRating by remember { mutableFloatStateOf(state.tvShowDetailsUiState.selectedRating) }
    val scrollState = rememberLazyGridState()
    val isCollapsed by remember {
        derivedStateOf {
            scrollState.firstVisibleItemScrollOffset > 50 || scrollState.firstVisibleItemIndex > 0
        }
    }
    val mediaList = state.tvShowDetailsUiState.recommendations.collectAsLazyPagingItems()

    val defaultIndex = tvChips.indexOf(TvShowChips.SEASONS)
    val selectedIndex = rememberSaveable { mutableIntStateOf(defaultIndex) }

    val expandedStates = rememberSaveable(state.tvShowDetailsUiState.tvShowUi.seasons.size) {
        mutableStateOf(List(state.tvShowDetailsUiState.tvShowUi.seasons.size) { false })
    }
    val reviewsList = state.tvShowDetailsUiState.reviews

    val screenHeight = with(LocalDensity){ LocalWindowInfo.current.containerSize.height.dp }

    LaunchedEffect(isCollapsed) {
        if (isCollapsed && scrollState.layoutInfo.totalItemsCount > 0) {
            scrollState.animateScrollToItem(
                index = scrollState.layoutInfo.totalItemsCount - 1
            )
        }
    }

    if (state.showRatingDialog) {
        RatingDialog(
            currentRating = currentRating,
            onRatingChange = { newRating ->
                currentRating = newRating
            },
            onDismiss = { tvShowScreenInteractionListener.onDismissRatingDialog() },
            onSubmit = {
                tvShowScreenInteractionListener.onRatingSubmitted(
                    movieId = state.tvShowDetailsUiState.tvShowUi.id,
                    rating = currentRating
                )
            }
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .background(Theme.colors.surface)
                .navigationBarsPadding()
        ) {
            when {
                state.isLoading -> {
                    PageLoadingPlaceHolder(
                        modifier = Modifier.fillMaxSize()
                    )
                }

                else -> {
                    if (state.tvShowDetailsUiState.isYoutubePlayerVisible &&
                        !state.tvShowDetailsUiState.youtubeVideoKey.isNullOrEmpty()
                    ) {
                        VideoPlayer(
                            videoKey = state.tvShowDetailsUiState.youtubeVideoKey,
                            onCloseClick = {
                                tvShowScreenInteractionListener.closeYoutubePlayer()
                            },
                        )

                    } else {
                        SharedTransitionLayout {
                            AnimatedContent(
                                targetState = isCollapsed,
                                label = "basic_transition"
                            ) { target ->
                                if (!target) {
                                    Box {
                                        TopComponentDetails(
                                            state = state,
                                            tvShowScreenInteractionListener = tvShowScreenInteractionListener,
                                            animatedVisibilityScope = this@AnimatedContent,
                                            sharedTransitionScope = this@SharedTransitionLayout,
                                            listState = scrollState
                                        )
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(360.dp)
                                                .align(Alignment.TopCenter)
                                                .background(
                                                    brush = Brush.verticalGradient(
                                                        colors = Theme.colors.gradient.overlyDark.asReversed()
                                                    )
                                                )
                                        )
                                    }
                                } else {
                                    TvTopComponent(
                                        tvShowScreenInteractionListener = tvShowScreenInteractionListener,
                                        animatedVisibilityScope = this@AnimatedContent,
                                        sharedTransitionScope = this@SharedTransitionLayout,
                                        title = state.tvShowDetailsUiState.tvShowUi.title,
                                        state = state
                                    )
                                }
                            }
                        }
                    }
                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(150.dp),
                        state = scrollState,
                        modifier = Modifier
                            .fillMaxSize()
                            .navigationBarsPadding()
                    )
                    {
                        item (span = {GridItemSpan(maxLineSpan)}){
                            DescriptionSection(
                                title = state.tvShowDetailsUiState.tvShowUi.title,
                                genres = state.tvShowDetailsUiState.tvShowUi.genres,
                                releaseDate = state.tvShowDetailsUiState.tvShowUi.releaseDate,
                                runtime = state.tvShowDetailsUiState.tvShowUi.runtime,
                                country = state.tvShowDetailsUiState.tvShowUi.country,
                                description = state.tvShowDetailsUiState.tvShowUi.description
                            )
                        }
                        item (span = {GridItemSpan(maxLineSpan)}){
                            if (state.tvShowDetailsUiState.cast.isNotEmpty()) {
                                CastSection(
                                    castList = state.tvShowDetailsUiState.cast,
                                    onSeeAllClick = {
                                        tvShowScreenInteractionListener.onShowAllCastClick(
                                            state.tvShowDetailsUiState.tvShowUi.id
                                        )
                                    }
                                )
                            }
                        }
                        item (span = {GridItemSpan(maxLineSpan)}){
                            ChipsRowSection(
                                items = tvChips.map {
                                    stringResource(it.titleResId) to it.iconResId
                                },
                                selectedIndex = selectedIndex.intValue,
                                onItemSelected = { selectedIndex.intValue = it }
                            )
                        }

                        selectedIndex.intValue.let { index ->
                            when (tvChips[index]) {
                                TvShowChips.SEASONS -> {

                                    if (state.tvShowDetailsUiState.tvShowUi.seasons.isEmpty()) {
                                        item (span = {GridItemSpan(maxLineSpan)}){
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .background(Theme.colors.surface)
                                                    .padding(vertical = 30.dp)
                                                    .navigationBarsPadding(),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                AppText(
                                                    text = stringResource(R.string.there_is_no_seasons),
                                                    style = Theme.textStyle.label.large,
                                                    color = Theme.colors.text.body.copy(alpha = 0.6f)
                                                )
                                            }
                                        }
                                    } else {
                                        state.tvShowDetailsUiState.tvShowUi.seasons.forEachIndexed { seasonIndex, season ->
                                            val isExpanded = expandedStates.value[seasonIndex]
                                            val isSeasonLoading =
                                                state.seasonsLoadingStates[season.seasonNumber] == true

                                            stickyHeader {
                                                SeasonHeader(
                                                    seasonNumber = seasonIndex + 1,
                                                    numberOfEpisodes = season.episodeCount,
                                                    isExpanded = isExpanded,
                                                    onToggleExpand = {
                                                        expandedStates.value =
                                                            expandedStates.value.toMutableList()
                                                                .also {
                                                                    it[seasonIndex] =
                                                                        !it[seasonIndex]
                                                                }
                                                        tvShowScreenInteractionListener.onClickOnSeason(
                                                            season.seasonNumber
                                                        )
                                                    },
                                                )
                                            }

                                            if (isExpanded) {
                                                if (isSeasonLoading) {
                                                    item (span = {GridItemSpan(maxLineSpan)}){
                                                        Box(
                                                            modifier = Modifier
                                                                .fillMaxWidth()
                                                                .padding(16.dp),
                                                            contentAlignment = Alignment.Center
                                                        ) {
                                                            PageLoadingPlaceHolder()
                                                        }
                                                    }
                                                } else {
                                                    items(season.episodes.size,span = {GridItemSpan(maxLineSpan)}) { episodeIndex ->
                                                        val episode = season.episodes[episodeIndex]
                                                        AnimatedVisibility(
                                                            visible = true,
                                                            enter = expandVertically(
                                                                animationSpec = tween(
                                                                    300
                                                                )
                                                            ) + fadeIn(),
                                                            exit = shrinkVertically(
                                                                animationSpec = tween(
                                                                    300
                                                                )
                                                            ) + fadeOut()
                                                        ) {

                                                            AnimatedContent(
                                                                targetState = episode.episodePhotoUrl.ifEmpty { state.tvShowDetailsUiState.tvShowUi.posterUrl },
                                                                transitionSpec = {
                                                                    fadeIn(animationSpec = tween(300)) togetherWith
                                                                            fadeOut(
                                                                                animationSpec = tween(
                                                                                    300
                                                                                )
                                                                            )
                                                                },
                                                                label = "image_transition"
                                                            ) { value ->
                                                                EpisodeCard(
                                                                    episodeRating = episode.voteAverage?.toFloat(),
                                                                    episodeNumber = episode.episodeNumber.toString(),
                                                                    episodeTitle = episode.episodeNumber.toString(),
                                                                    episodeDuration = episode.runtime,
                                                                    imageUri = value,
                                                                    episodeDate = episode.airDate,
                                                                    episodeDescription = episode.description,
                                                                    modifier = Modifier
                                                                        .fillMaxWidth()
                                                                        .padding(horizontal = 8.dp),
                                                                    hasVideo = true,
                                                                    nsfwThreshold = state.nsfwThreshold,
                                                                    genderThreshold = state.genderThreshold,
                                                                    onPlayClick = {
                                                                        tvShowScreenInteractionListener.onClickPlayEpisodeTrailer(
                                                                            state.tvShowDetailsUiState.tvShowUi.id,
                                                                            seasonIndex + 1,
                                                                            episode.episodeNumber
                                                                        )
                                                                        if (state.tvShowDetailsUiState.episodeVideoUi.site.isNotEmpty() &&
                                                                            state.tvShowDetailsUiState.episodeVideoUi.key.isNotEmpty()
                                                                        ) {
                                                                            tvShowScreenInteractionListener.playYoutubeVideo(
                                                                                state.tvShowDetailsUiState.episodeVideoUi.key
                                                                            )
                                                                        }
                                                                    }
                                                                )
                                                            }

                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }

                                TvShowChips.MORE_LIKE_THIS ->
                                    if (mediaList.itemSnapshotList.isEmpty()) {
                                        item (span = {GridItemSpan(maxLineSpan)}){
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .background(Theme.colors.surface)
                                                    .padding(vertical = 30.dp)
                                                    .navigationBarsPadding(),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                AppText(
                                                    text = stringResource(R.string.there_is_no_recommendations),
                                                    style = Theme.textStyle.label.large,
                                                    color = Theme.colors.text.body.copy(alpha = 0.6f)
                                                )
                                            }
                                        }
                                    } else {
                                        items(mediaList.itemCount,span = {GridItemSpan(maxLineSpan)}) { mediaIndex ->
                                            mediaList[mediaIndex]?.let { media ->
                                                MediaCard(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .padding(
                                                            start = 16.dp,
                                                            end = 16.dp,
                                                            bottom = 8.dp
                                                        ),
                                                    imageUri = media.posterPath,
                                                    rating = media.voteAverage?.toFloat(),
                                                    movieName = media.title,
                                                    nsfwThreshold = state.nsfwThreshold,
                                                    genderThreshold = state.genderThreshold,
                                                    mediaType = stringResource(
                                                        featureMediaDetailsUiR.string.tvshow
                                                    ),
                                                    year = media.releaseDate.take(4),
                                                    mediaCardType = MediaCardType.UP_COMING,
                                                    showGradientFilter = true,
                                                    clickable = true,
                                                    onClick = {
                                                        tvShowScreenInteractionListener.onSimilarTvShowClick(
                                                            media.id
                                                        )
                                                    },
                                                    cardWidth = null
                                                )
                                            }
                                        }
                                    }

                                TvShowChips.REVIEWS ->
                                    if (reviewsList.isEmpty()) {
                                        item (span = {GridItemSpan(maxLineSpan)}){
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .background(Theme.colors.surface)
                                                    .padding(vertical = 30.dp)
                                                    .navigationBarsPadding(),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                AppText(
                                                    text = stringResource(R.string.there_is_no_reviews),
                                                    style = Theme.textStyle.label.large,
                                                    color = Theme.colors.text.body.copy(alpha = 0.6f)
                                                )
                                            }
                                        }
                                    } else {
                                        items(reviewsList,span = {GridItemSpan(maxLineSpan)}) { review ->
                                            ReviewsSection(review)
                                        }
                                    }

                                TvShowChips.GALLERY ->  {
                                    if (state.tvShowDetailsUiState.gallery.isEmpty()) {
                                        item (span = {GridItemSpan(maxLineSpan)}){
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .background(Theme.colors.surface)
                                                    .padding(vertical = 30.dp)
                                                    .navigationBarsPadding(),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                AppText(
                                                    text = stringResource(R.string.there_is_no_gallery),
                                                    style = Theme.textStyle.label.large,
                                                    color = Theme.colors.text.body.copy(alpha = 0.6f)
                                                )
                                            }
                                        }
                                    } else {
                                        gallerySection(state.tvShowDetailsUiState.gallery)
                                    }
                                }

                                TvShowChips.COMPANY_PRODUCTION ->
                                    productionCompanySection(
                                        companies = state.tvShowDetailsUiState.tvShowUi.productionCompanies
                                    )

                            }
                        }

                        item (span = {GridItemSpan(maxLineSpan)}){
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(screenHeight / 26),
                                contentAlignment = Alignment.Center
                            ) {
                                AppText(
                                    text = stringResource(R.string.no_more_items),
                                    style = Theme.textStyle.label.small,
                                    color = Theme.colors.text.body.copy(alpha = 0.6f)

                                )
                            }
                        }
                    }
                }
            }
        }
        AnimatedVisibility(
            visible = state.showSnackBar,
            enter = fadeIn() + slideInVertically(),
            exit = fadeOut() + slideOutVertically()
        ) {
            AppSnackBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(start = 12.dp, end = 12.dp, top = 16.dp)
                    .align(Alignment.TopCenter),
                text = state.snackBarMessage ?: designsystemR.string.empty,
                isSuccess = state.snackBarSuccess,
                onClick = tvShowScreenInteractionListener::onHideSnackBar
            )
        }
    }
}