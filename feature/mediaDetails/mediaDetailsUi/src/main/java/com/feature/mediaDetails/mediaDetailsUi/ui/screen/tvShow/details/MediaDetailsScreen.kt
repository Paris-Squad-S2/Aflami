package com.feature.mediaDetails.mediaDetailsUi.ui.screen.tvShow.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.ChipsRowSection
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.GallerySection
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.MoreLikeThisSection
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.castSection.CastSection
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.companyProductionSection.ProductionCompanySection
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.descriptionSection.DescriptionSection
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.detailsImage.DetailsImage
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.reviewSection.ReviewsSection
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.seasonSection.SeasonSection
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.hasDescriptionContent
import com.paris_2.aflami.designsystem.R
import com.paris_2.aflami.designsystem.components.NetworkError
import com.paris_2.aflami.designsystem.components.PageLoadingPlaceHolder
import com.paris_2.aflami.designsystem.components.PlaceholderView
import com.paris_2.aflami.designsystem.components.TopAppBar
import com.paris_2.aflami.designsystem.components.iconItemWithDefaults
import com.paris_2.aflami.designsystem.theme.Theme
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun TvShowDetailsScreen(viewModel: TvShowDetailsViewModelViewModel = koinViewModel()) {
    val state = viewModel.screenState.collectAsStateWithLifecycle()
    TvShowDetailsScreenContent(state = state.value, tvShowScreenInteractionListener = viewModel)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TvShowDetailsScreenContent(
    state: TvShowDetailsScreenState,
    tvShowScreenInteractionListener: TvShowScreenInteractionListener,
) {
    val tvChips = TvShowChips.entries
    val defaultIndex = tvChips.indexOf(TvShowChips.SEASONS)
    val selectedIndex = rememberSaveable { mutableIntStateOf(defaultIndex) }
    val rate = stringResource(com.feature.mediaDetails.mediaDetailsUi.R.string.rate)
    val addToList = stringResource(com.feature.mediaDetails.mediaDetailsUi.R.string.add_to_list)

    val expandedStates = rememberSaveable(state.tvShowDetailsUiState.tvShowUi.seasons.size) {
        mutableStateOf(List(state.tvShowDetailsUiState.tvShowUi.seasons.size) { false })
    }

    Box(
        Modifier
            .fillMaxSize()
            .background(Theme.colors.surface)
            .navigationBarsPadding()
            .statusBarsPadding()
    ) {
        when {
            state.errorMessage != null -> {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    TopAppBar(
                        leadingIcons = listOf(
                            iconItemWithDefaults(
                                icon = ImageVector.vectorResource(R.drawable.ic_back),
                                onClick = tvShowScreenInteractionListener::onNavigateBack
                            )
                        )
                    )
                    NetworkError(
                        modifier = Modifier.fillMaxSize(),
                        onRetry = tvShowScreenInteractionListener::onRetryLoadTvShowDetails
                    )
                }
            }

            state.isLoading -> {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    TopAppBar(
                        leadingIcons = listOf(
                            iconItemWithDefaults(
                                icon = ImageVector.vectorResource(R.drawable.ic_back),
                                onClick = tvShowScreenInteractionListener::onNavigateBack
                            )
                        )
                    )
                    PageLoadingPlaceHolder(
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            !state.isLoading && state.tvShowDetailsUiState.tvShowUi.title.isEmpty() -> {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    TopAppBar(
                        leadingIcons = listOf(
                            iconItemWithDefaults(
                                icon = ImageVector.vectorResource(R.drawable.ic_back),
                                onClick = tvShowScreenInteractionListener::onNavigateBack
                            )
                        )
                    )
                    PlaceholderView(
                        modifier = Modifier.fillMaxSize(),
                        image = painterResource(R.drawable.ic_network_error),
                        title = stringResource(com.feature.mediaDetails.mediaDetailsUi.R.string.no_tvshow_details),
                        subTitle = stringResource(com.feature.mediaDetails.mediaDetailsUi.R.string.tvshow_details_not_available),
                        spacer = 16.dp
                    )
                }
            }

            else -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .navigationBarsPadding()
                ) {
                    item {
                        if (state.isImageLoading) {
                            PageLoadingPlaceHolder(
                                modifier = Modifier.padding(bottom = 12.dp)
                            )
                        } else {
                            DetailsImage(
                                imageUris = listOf(
                                    state.tvShowDetailsUiState.tvShowUi.posterUrl,
                                ),
                                rating = state.tvShowDetailsUiState.tvShowUi.rating,
                                onPlayClick = {},
                                modifier = Modifier.padding(bottom = 12.dp)
                            )
                        }
                    }

                    if (state.isDescriptionLoading) {
                        item {
                            PageLoadingPlaceHolder(
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    } else if (hasDescriptionContent(state.tvShowDetailsUiState.tvShowUi)) {
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
                    }

                    if (state.isCastLoading) {
                        item {
                            PageLoadingPlaceHolder(
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    } else if (state.tvShowDetailsUiState.cast.isNotEmpty()) {
                        item {
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
                                if (state.isSeasonsLoading) {
                                    item {
                                        PageLoadingPlaceHolder(
                                            modifier = Modifier.padding(16.dp)
                                        )
                                    }
                                } else {
                                    items(state.tvShowDetailsUiState.tvShowUi.seasons.size) { seasonIndex ->
                                        val season = state.tvShowDetailsUiState.tvShowUi.seasons[seasonIndex]
                                        val isExpanded = expandedStates.value[seasonIndex]

                                        val isSeasonLoading = state.seasonsLoadingStates[season.seasonNumber] ?: false

                                        SeasonSection(
                                            seasonNumber = seasonIndex + 1,
                                            numberOfEpisodes = season.episodeCount,
                                            episodes = season.episodes,
                                            isExpanded = isExpanded,
                                            isLoading = isSeasonLoading,
                                            onToggleExpand = {
                                                expandedStates.value =
                                                    expandedStates.value.toMutableList().also {
                                                        it[seasonIndex] = !it[seasonIndex]
                                                    }

                                                if (!isExpanded) {
                                                    tvShowScreenInteractionListener.onClickOnSeason(
                                                        seasonNumber = season.seasonNumber
                                                    )
                                                }
                                            }
                                        )
                                    }
                                }
                            }

                            TvShowChips.MORE_LIKE_THIS -> item {
                                if (state.isRecommendationsLoading) {
                                    PageLoadingPlaceHolder(
                                        modifier = Modifier.padding(16.dp)
                                    )
                                } else {
                                    MoreLikeThisSection(
                                        mediaList = state.tvShowDetailsUiState.recommendations,
                                        onClick = {},
                                        mediaType = stringResource(com.feature.mediaDetails.mediaDetailsUi.R.string.tvshow)
                                    )
                                }
                            }

                            TvShowChips.REVIEWS -> item {
                                if (state.isReviewsLoading) {
                                    PageLoadingPlaceHolder(
                                        modifier = Modifier.padding(16.dp)
                                    )
                                } else {
                                    ReviewsSection(
                                        reviews = state.tvShowDetailsUiState.reviews.takeIf { it.isNotEmpty() }
                                    )
                                }
                            }

                            TvShowChips.GALLERY -> item {
                                if (state.isGalleryLoading) {
                                    PageLoadingPlaceHolder(
                                        modifier = Modifier.padding(16.dp)
                                    )
                                } else {
                                    GallerySection(state.tvShowDetailsUiState.gallery)
                                }
                            }

                            TvShowChips.COMPANY_PRODUCTION -> item {
                                if (state.isProductionCompaniesLoading) {
                                    PageLoadingPlaceHolder(
                                        modifier = Modifier.padding(16.dp)
                                    )
                                } else {
                                    ProductionCompanySection(
                                        companies = state.tvShowDetailsUiState.tvShowUi.productionCompanies,
                                        modifier = Modifier
                                            .padding(horizontal = 16.dp)
                                            .padding(top = 12.dp)
                                    )
                                }
                            }
                        }
                    }
                }

                TopAppBar(
                    leadingIcons = listOf(
                        iconItemWithDefaults(
                            icon = ImageVector.vectorResource(R.drawable.ic_back),
                            onClick = { tvShowScreenInteractionListener.onNavigateBack() }
                        )
                    ),
                    trailingIcons = listOf(
                        iconItemWithDefaults(
                            icon = ImageVector.vectorResource(R.drawable.ic_star),
                            onClick = { tvShowScreenInteractionListener.onFavouriteClick(rate) }
                        ),
                        iconItemWithDefaults(
                            icon = ImageVector.vectorResource(R.drawable.ic_heart_add),
                            onClick = { tvShowScreenInteractionListener.onAddToListClick(addToList) }
                        )
                    )
                )
            }
        }
    }
}