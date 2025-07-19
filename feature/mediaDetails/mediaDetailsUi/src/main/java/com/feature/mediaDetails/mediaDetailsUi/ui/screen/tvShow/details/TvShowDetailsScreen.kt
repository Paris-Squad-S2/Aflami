package com.feature.mediaDetails.mediaDetailsUi.ui.screen.tvShow.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import com.paris_2.aflami.designsystem.R as designsystemR
import com.feature.mediaDetails.mediaDetailsUi.R as featureMediaDetailsUiR
import com.paris_2.aflami.designsystem.components.TopAppBar
import com.paris_2.aflami.designsystem.components.iconItemWithDefaults
import com.paris_2.aflami.designsystem.theme.Theme
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun TvShowDetailsScreen(viewModel: TvShowDetailsViewModel = koinViewModel()) {
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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding()
        ) {
            item {
                DetailsImage(
                    imageUris = listOf(
                        state.tvShowDetailsUiState.tvShowUi.posterUrl,
                    ),
                    rating = state.tvShowDetailsUiState.tvShowUi.rating,
                    onPlayClick = {},
                    modifier = Modifier.padding(bottom = 12.dp)
                )
            }
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
                if (state.tvShowDetailsUiState.cast.isNotEmpty())
                    CastSection(
                        castList = state.tvShowDetailsUiState.cast,
                        onSeeAllClick = {
                            tvShowScreenInteractionListener.onShowAllCastClick(
                                state.tvShowDetailsUiState.tvShowUi.id
                            )
                        }
                    )
            }
            item {

                ChipsRowSection(
                    items = tvChips.map {
                        stringResource(it.titleResId) to it.iconResId
                    },
                    selectedIndex = selectedIndex.intValue,
                    onItemSelected = { selectedIndex.intValue = it } // Always one selected
                )
            }

            selectedIndex.intValue.let { index ->
                when (tvChips[index]) {

                    TvShowChips.SEASONS -> {
                        items(state.tvShowDetailsUiState.tvShowUi.seasons.size) { seasonIndex ->
                            val season = state.tvShowDetailsUiState.tvShowUi.seasons[seasonIndex]
                            val isExpanded = expandedStates.value[seasonIndex]

                            SeasonSection(
                                seasonNumber = seasonIndex + 1,
                                numberOfEpisodes = season.episodeCount,
                                episodes = season.episodes,
                                isExpanded = isExpanded,
                                onToggleExpand = {
                                    expandedStates.value =
                                        expandedStates.value.toMutableList().also {
                                            it[seasonIndex] = !it[seasonIndex]
                                        }
                                    tvShowScreenInteractionListener.onClickOnSeason(
                                        seasonNumber = season.seasonNumber
                                    )
                                }
                            )
                        }
                    }

                    TvShowChips.MORE_LIKE_THIS -> item {
                        MoreLikeThisSection(
                            mediaList = state.tvShowDetailsUiState.recommendations,
                            onClick = {},
                            mediaType = stringResource(com.feature.mediaDetails.mediaDetailsUi.R.string.tvshow)
                        )
                    }

                    TvShowChips.REVIEWS -> item {
                        ReviewsSection(
                            reviews = state.tvShowDetailsUiState.reviews.takeIf { it.isNotEmpty() }
                        )
                    }

                    TvShowChips.GALLERY -> item {
                        GallerySection(state.tvShowDetailsUiState.gallery)
                    }

                    TvShowChips.COMPANY_PRODUCTION -> item {
                        ProductionCompanySection(
                            companies = state.tvShowDetailsUiState.tvShowUi.productionCompanies
                        )
                    }
                }
            }
        }

        TopAppBar(
            leadingIcons = listOf(
                iconItemWithDefaults(
                    icon = ImageVector.vectorResource(designsystemR.drawable.ic_back),
                    onClick = { tvShowScreenInteractionListener.onNavigateBack() }
                )
            ),
            trailingIcons = listOf(
                iconItemWithDefaults(
                    icon = ImageVector.vectorResource(designsystemR.drawable.ic_star),
                    onClick = {
                        tvShowScreenInteractionListener.onFavouriteClick(
                            featureMediaDetailsUiR.string.rate
                        )
                    }
                ),
                iconItemWithDefaults(
                    icon = ImageVector.vectorResource(designsystemR.drawable.ic_heart_add),
                    onClick = {
                        tvShowScreenInteractionListener.onAddToListClick(
                            featureMediaDetailsUiR.string.add_to_list
                        )
                    }
                )
            )
        )
    }
}