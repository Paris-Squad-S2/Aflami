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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.domain.mediaDetails.model.Episode
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.ChipsRowSection
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.GallerySection
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.MoreLikeThisSection
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.castSection.CastSection
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.companyProductionSection.ProductionCompanySection
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.descriptionSection.DescriptionSection
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.detailsImage.DetailsImage
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.reviewSection.ReviewsSection
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.seasonSection.SeasonSection
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.tvShow.details.TvShowChips
import com.paris_2.aflami.designsystem.R
import com.paris_2.aflami.designsystem.components.TopAppBar
import com.paris_2.aflami.designsystem.components.iconItemWithDefaults
import com.paris_2.aflami.designsystem.theme.Theme
import kotlinx.datetime.LocalDate
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
    val selectedIndex = rememberSaveable { mutableStateOf<Int?>(null) }
    val tvChips = TvShowChips.entries
    val rate = stringResource(com.feature.mediaDetails.mediaDetailsUi.R.string.rate)
    val addToList = stringResource(com.feature.mediaDetails.mediaDetailsUi.R.string.add_to_list)

    val expandedStates = rememberSaveable(state.tvShowDetailsUiState.seasons.size) {
        mutableStateOf(List(state.tvShowDetailsUiState.seasons.size) { false })
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
                    selectedIndex = selectedIndex.value,
                    onItemSelected = { selectedIndex.value = it }
                )
            }

            selectedIndex.value?.let { index ->
                when (tvChips[index]) {

                    TvShowChips.SEASONS -> {
                        items(state.tvShowDetailsUiState.seasons.size) { seasonIndex ->
                            val season = state.tvShowDetailsUiState.seasons[seasonIndex]
                            val isExpanded = expandedStates.value[seasonIndex]

                            SeasonSection(
                                seasonNumber = seasonIndex + 1,
                                numberOfEpisodes = season.episodes.size,
                                episodes = season.episodes.map {
                                    Episode(
                                        id = it.episodeNumber,
                                        episodeNumber = it.episodeNumber,
                                        posterUrl = it.posterUrl,
                                        voteAverage = it.voteAverage,
                                        airDate = LocalDate.parse(it.airDate),
                                        runtime = it.runtime.toInt(),
                                        description = it.description,
                                        stillUrl = it.stillUrl
                                    )
                                },
                                isExpanded = isExpanded,
                                onToggleExpand = {
                                    expandedStates.value =
                                        expandedStates.value.toMutableList().also {
                                            it[seasonIndex] = !it[seasonIndex]
                                        }
                                }
                            )
                        }
                    }

                    TvShowChips.MORE_LIKE_THIS -> item {
                        MoreLikeThisSection(
                            mediaList = listOf(
                                state.tvShowDetailsUiState.tvShowUi,
                                state.tvShowDetailsUiState.tvShowUi.copy(title = stringResource(com.feature.mediaDetails.mediaDetailsUi.R.string.another_tvshow))
                            ),
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