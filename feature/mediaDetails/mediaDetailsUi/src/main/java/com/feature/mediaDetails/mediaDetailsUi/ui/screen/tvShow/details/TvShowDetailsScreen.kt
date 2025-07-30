package com.feature.mediaDetails.mediaDetailsUi.ui.screen.tvShow.details

import android.annotation.SuppressLint
import androidx.activity.compose.LocalActivity
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.feature.mediaDetails.mediaDetailsUi.R
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.AddToListDialog
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.ChipsRowSection
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.GallerySection
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.RatingDialog
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.TopComponentDetails
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.TvTopComponent
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.castSection.CastSection
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.companyProductionSection.ProductionCompanySection
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.descriptionSection.DescriptionSection
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.reviewSection.ReviewsSection
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.seasonSection.SeasonHeader
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.openYoutubeOrBrowser
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.movie.details.ProductionCompanyUi
import com.paris_2.aflami.designsystem.components.EpisodeCard
import com.paris_2.aflami.designsystem.components.MediaCard
import com.paris_2.aflami.designsystem.components.MediaCardType
import com.paris_2.aflami.designsystem.components.PageLoadingPlaceHolder
import com.paris_2.aflami.designsystem.components.SnackBar
import com.paris_2.aflami.designsystem.theme.Theme
import kotlinx.coroutines.flow.emptyFlow
import com.feature.mediaDetails.mediaDetailsUi.R as featureMediaDetailsUiR
import com.paris_2.aflami.designsystem.R as designsystemR


@Composable
fun TvShowDetailsScreen(viewModel: TvShowDetailsViewModel = hiltViewModel()) {
    val state = viewModel.screenState.collectAsStateWithLifecycle()

    TvShowDetailsScreenContent(
        state = state.value,
        tvShowScreenInteractionListener = viewModel
    )
}

@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
fun TvShowDetailsScreenContent(
    state: TvShowDetailsScreenState,
    tvShowScreenInteractionListener: TvShowScreenInteractionListener,
) {
    val tvChips = TvShowChips.entries
    val activity = LocalActivity.current
    var currentRating by remember { mutableFloatStateOf(state.tvShowDetailsUiState.selectedRating) }

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
    if (state.showAddToListDialog) {
        AddToListDialog(
            list = listOf("My Favorite Movies", "Kittens"),
            onDismiss = { tvShowScreenInteractionListener.onDismissAddToListDialog() },
        )
    }


    val defaultIndex = tvChips.indexOf(TvShowChips.SEASONS)
    val selectedIndex = rememberSaveable { mutableIntStateOf(defaultIndex) }

    val expandedStates = rememberSaveable(state.tvShowDetailsUiState.tvShowUi.seasons.size) {
        mutableStateOf(List(state.tvShowDetailsUiState.tvShowUi.seasons.size) { false })
    }
    val reviewsList = state.tvShowDetailsUiState.reviews.collectAsLazyPagingItems()

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
                    val scrollState = rememberLazyListState()
                    val isCollapsed by remember {
                        derivedStateOf {
                            scrollState.firstVisibleItemScrollOffset > 50 || scrollState.firstVisibleItemIndex > 0
                        }
                    }
                    val mediaList =
                        state.tvShowDetailsUiState.recommendations.collectAsLazyPagingItems()

                    SharedTransitionLayout {
                        AnimatedContent(
                            targetState = isCollapsed,
                            label = "basic_transition"
                        ) { target ->
                            if (!target) {
                                TopComponentDetails(
                                    state = state,
                                    tvShowScreenInteractionListener = tvShowScreenInteractionListener,
                                    animatedVisibilityScope = this@AnimatedContent,
                                    sharedTransitionScope = this@SharedTransitionLayout,
                                )
                            } else {
                                TvTopComponent(
                                    tvShowScreenInteractionListener = tvShowScreenInteractionListener,
                                    animatedVisibilityScope = this@AnimatedContent,
                                    sharedTransitionScope = this@SharedTransitionLayout,
                                    title = state.tvShowDetailsUiState.tvShowUi.title,
                                )
                            }

                        }
                    }
                    LazyColumn(
                        state = scrollState,
                        modifier = Modifier
                            .fillMaxSize()
                            .navigationBarsPadding()
                    )
                    {
                        item {
                            DescriptionSection(
                                title = state.tvShowDetailsUiState.tvShowUi.title,
                                genres = state.tvShowDetailsUiState.tvShowUi.genres,
                                releaseDate = state.tvShowDetailsUiState.tvShowUi.releaseDate,
                                runtime = state.tvShowDetailsUiState.tvShowUi.runtime,
                                country = state.tvShowDetailsUiState.tvShowUi.country,
                                description = state.tvShowDetailsUiState.tvShowUi.description
                            )
                        }
                        item {
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
                        item {
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
                                        item {
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .background(Theme.colors.surface)
                                                    .padding(vertical = 30.dp)
                                                    .navigationBarsPadding(),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Text(
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
                                                    item {

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
                                                    items(season.episodes.size) { episodeIndex ->
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
                                                                targetState = if (episode.stillUrl.isNotEmpty()) episode.stillUrl else state.tvShowDetailsUiState.tvShowUi.posterUrl,
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
                                                                    onPlayClick = {
                                                                        tvShowScreenInteractionListener.onClickPlayEpisodeTrailer(
                                                                            state.tvShowDetailsUiState.tvShowUi.id,
                                                                            seasonIndex + 1,
                                                                            episode.episodeNumber
                                                                        )
                                                                        if (!(state.tvShowDetailsUiState.episodeVideoUi.site.isEmpty() ||
                                                                                    state.tvShowDetailsUiState.episodeVideoUi.key.isEmpty())
                                                                        ) {
                                                                            activity?.openYoutubeOrBrowser(
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
                                        item {
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .background(Theme.colors.surface)
                                                    .padding(vertical = 30.dp)
                                                    .navigationBarsPadding(),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Text(
                                                    text = stringResource(R.string.there_is_no_recommendations),
                                                    style = Theme.textStyle.label.large,
                                                    color = Theme.colors.text.body.copy(alpha = 0.6f)
                                                )
                                            }
                                        }
                                    } else {
                                        items(mediaList.itemCount) { mediaIndex ->
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
                                    if (reviewsList.itemSnapshotList.isEmpty()) {
                                        item {
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .background(Theme.colors.surface)
                                                    .padding(vertical = 30.dp)
                                                    .navigationBarsPadding(),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Text(
                                                    text = stringResource(R.string.there_is_no_reviews),
                                                    style = Theme.textStyle.label.large,
                                                    color = Theme.colors.text.body.copy(alpha = 0.6f)
                                                )
                                            }
                                        }
                                    } else {
                                        items(reviewsList.itemCount) { index ->
                                            ReviewsSection(reviewsList[index])
                                        }
                                    }

                                TvShowChips.GALLERY -> item {
                                    if (state.tvShowDetailsUiState.gallery.isEmpty()) {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .background(Theme.colors.surface)
                                                .padding(vertical = 30.dp)
                                                .navigationBarsPadding(),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                text = stringResource(R.string.there_is_no_gallery),
                                                style = Theme.textStyle.label.large,
                                                color = Theme.colors.text.body.copy(alpha = 0.6f)
                                            )
                                        }
                                    } else {
                                        GallerySection(state.tvShowDetailsUiState.gallery)
                                    }
                                }

                                TvShowChips.COMPANY_PRODUCTION -> item {
                                    ProductionCompanySection(
                                        companies = state.tvShowDetailsUiState.tvShowUi.productionCompanies
                                    )
                                }
                            }
                        }

                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp)
                                    .navigationBarsPadding()
                            )
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
            SnackBar(
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

@Preview(showSystemUi = true)
@Composable
fun PreviewTvShowDetailsScreen() {
    val fakeTvShowDetailsUiState = TvShowDetailsUiState(
        tvShowUi = TvShowUi(
            id = 1,
            posterUrl = "",
            rating = 8.5f,
            title = "Stranger Things",
            genres = listOf("Drama", "Sci-Fi", "Horror"),
            releaseDate = "2016-07-15",
            runtime = "50 min",
            country = "USA",
            description = "When a young boy vanishes, a small town uncovers a mystery involving secret experiments, terrifying supernatural forces and one strange little girl.",
            productionCompanies = listOf(
                ProductionCompanyUi("Netflix", "", ""),
                ProductionCompanyUi("21 Laps", "", "")
            ),
            seasons = listOf(
                SeasonUi(
                    id = 1,
                    name = "Season 1",
                    episodeCount = 8,
                    seasonNumber = 1,
                    isExpanded = true,
                    episodes = List(8) { index ->
                        EpisodeUi(
                            episodeNumber = index + 1,
                            posterUrl = "",
                            voteAverage = 8.5,
                            airDate = "2016-07-${15 + index}",
                            runtime = "${45 + index} min",
                            description = "Episode ${index + 1} description...",
                            stillUrl = ""
                        )
                    }
                ),
                SeasonUi(
                    id = 2,
                    name = "Season 2",
                    episodeCount = 9,
                    seasonNumber = 2,
                    isExpanded = true,
                    episodes = List(2) { index ->
                        EpisodeUi(
                            episodeNumber = index + 1,
                            posterUrl = "",
                            voteAverage = 8.7,
                            airDate = "2017-10-${20 + index}",
                            runtime = "${50 + index} min",
                            description = "Episode ${index + 1} description...",
                            stillUrl = ""
                        )
                    }
                ),
                SeasonUi(
                    id = 3,
                    name = "Season 3",
                    episodeCount = 8,
                    seasonNumber = 1,
                    isExpanded = true,
                    episodes = List(8) { index ->
                        EpisodeUi(
                            episodeNumber = index + 1,
                            posterUrl = "",
                            voteAverage = 8.5,
                            airDate = "2016-07-${15 + index}",
                            runtime = "${45 + index} min",
                            description = "Episode ${index + 1} description...",
                            stillUrl = ""
                        )
                    }
                ),
                SeasonUi(
                    id = 1,
                    name = "Season 1",
                    episodeCount = 8,
                    seasonNumber = 1,
                    isExpanded = true,
                    episodes = List(3) { index ->
                        EpisodeUi(
                            episodeNumber = index + 1,
                            posterUrl = "",
                            voteAverage = 8.5,
                            airDate = "2016-07-${15 + index}",
                            runtime = "${45 + index} min",
                            description = "Episode ${index + 1} description...",
                            stillUrl = ""
                        )
                    }
                ),
                SeasonUi(
                    id = 1,
                    name = "Season 1",
                    episodeCount = 8,
                    seasonNumber = 1,
                    isExpanded = true,
                    episodes = List(3) { index ->
                        EpisodeUi(
                            episodeNumber = index + 1,
                            posterUrl = "",
                            voteAverage = 8.5,
                            airDate = "2016-07-${15 + index}",
                            runtime = "${45 + index} min",
                            description = "Episode ${index + 1} description...",
                            stillUrl = ""
                        )
                    }
                ),
                SeasonUi(
                    id = 1,
                    name = "Season 1",
                    episodeCount = 8,
                    seasonNumber = 1,
                    isExpanded = true,
                    episodes = List(3) { index ->
                        EpisodeUi(
                            episodeNumber = index + 1,
                            posterUrl = "",
                            voteAverage = 8.5,
                            airDate = "2016-07-${15 + index}",
                            runtime = "${45 + index} min",
                            description = "Episode ${index + 1} description...",
                            stillUrl = ""
                        )
                    }
                ),
                SeasonUi(
                    id = 1,
                    name = "Season 1",
                    episodeCount = 8,
                    seasonNumber = 1,
                    isExpanded = true,
                    episodes = List(3) { index ->
                        EpisodeUi(
                            episodeNumber = index + 1,
                            posterUrl = "",
                            voteAverage = 8.5,
                            airDate = "2016-07-${15 + index}",
                            runtime = "${45 + index} min",
                            description = "Episode ${index + 1} description...",
                            stillUrl = ""
                        )
                    }
                ),
                SeasonUi(
                    id = 1,
                    name = "Season 1",
                    episodeCount = 8,
                    seasonNumber = 1,
                    isExpanded = true,
                    episodes = List(3) { index ->
                        EpisodeUi(
                            episodeNumber = index + 1,
                            posterUrl = "",
                            voteAverage = 8.5,
                            airDate = "2016-07-${15 + index}",
                            runtime = "${45 + index} min",
                            description = "Episode ${index + 1} description...",
                            stillUrl = ""
                        )
                    }
                ),
                SeasonUi(
                    id = 1,
                    name = "Season 1",
                    episodeCount = 8,
                    seasonNumber = 1,
                    isExpanded = true,
                    episodes = List(3) { index ->
                        EpisodeUi(
                            episodeNumber = index + 1,
                            posterUrl = "",
                            voteAverage = 8.5,
                            airDate = "2016-07-${15 + index}",
                            runtime = "${45 + index} min",
                            description = "Episode ${index + 1} description...",
                            stillUrl = ""
                        )
                    }
                ),
            )
        ),
        recommendations = emptyFlow(),
        cast = emptyList(),
        reviews = emptyFlow(),
        gallery = listOf(),
        tvShowVideoUi = TvShowVideoUi("", "", ""),
        selectedRating = 0f,
        episodeVideoUi = EpisodeVideoUi("", "", "")
    )

    TvShowDetailsScreenContent(
        state = TvShowDetailsScreenState(
            tvShowDetailsUiState = fakeTvShowDetailsUiState,
            isLoading = false,
            errorMessage = null,
            isEpisodesLoading = true,
            isImageLoading = false,
            isDescriptionLoading = false,
            isCastLoading = true,
            isSeasonsLoading = false,
            isRecommendationsLoading = true,
            isReviewsLoading = false,
            isGalleryLoading = false,
            isProductionCompaniesLoading = false,
            seasonsLoadingStates = mapOf(
                1 to false,
                2 to false
            ),
            showRatingDialog = false,
            showSnackBar = false
        ),
        tvShowScreenInteractionListener = object : TvShowScreenInteractionListener {
            override fun onRateClick() {
            }

            override fun onAddToListClick() {
            }

            override fun onDismissAddToListDialog() {
            }

            override fun onShowAllCastClick(tvShowId: Int) {}
            override fun onClickOnSeason(seasonNumber: Int) {}
            override fun onSimilarTvShowClick(mediaId: Int) {}
            override fun onRetryLoadTvShowDetails() {}
            override fun onDismissRatingDialog() {}
            override fun onRatingSubmitted(movieId: Int, rating: Float) {
            }

            override fun onClickPlayEpisodeTrailer(
                tvShowId: Int,
                seasonNumber: Int,
                episodeNumber: Int
            ) {
            }

            override fun onHideSnackBar() {}
        }
    )
}

