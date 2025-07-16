package com.feature.mediaDetails.mediaDetailsUi.screen.tvShow

import SeasonHeader
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.domain.mediaDetails.model.Episode
import com.feature.mediaDetails.mediaDetailsUi.common.components.ChipsRowSection
import com.feature.mediaDetails.mediaDetailsUi.common.components.DescriptionSection
import com.feature.mediaDetails.mediaDetailsUi.common.components.GallerySection
import com.feature.mediaDetails.mediaDetailsUi.common.components.MoreLikeThisSection
import com.feature.mediaDetails.mediaDetailsUi.common.components.ProductionCompanySection
import com.feature.mediaDetails.mediaDetailsUi.common.components.ReviewsSection
import com.feature.mediaDetails.mediaDetailsUi.common.components.cast.CastSection
import com.feature.mediaDetails.mediaDetailsUi.common.components.detailsImage.DetailsImage
import com.feature.mediaDetails.mediaDetailsUi.screen.movie.CastUi
import com.feature.mediaDetails.mediaDetailsUi.screen.movie.ProductionCompanyUi
import com.feature.mediaDetails.mediaDetailsUi.screen.movie.ReviewUi
import com.paris_2.aflami.designsystem.R
import com.paris_2.aflami.designsystem.components.TopAppBar
import com.paris_2.aflami.designsystem.components.iconItemWithDefaults
import com.paris_2.aflami.designsystem.theme.AflamiTheme
import com.paris_2.aflami.designsystem.theme.Theme
import kotlinx.datetime.LocalDate
import com.paris_2.aflami.designsystem.R as RDesignSystem


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TvShowDetailsScreenContent(
    state: TvShowDetailsScreenState,
//    tvShowScreenInteractionListener: TvShowScreenInteractionListener
) {
    val selectedIndex = rememberSaveable { mutableStateOf<Int?>(null) }
    val chips = listOf(
        "Seasons" to R.drawable.ic_season,
        "More like this" to R.drawable.ic_camera_video,
        "Reviews" to R.drawable.ic_starr,
        "Gallery" to R.drawable.ic_album,
        "Company Production" to R.drawable.ic_city
    )

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
                    onPlayClick = {}
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
                    onSeeAllClick = {}
                )
            }
            item {
                ChipsRowSection(
                    items = chips,
                    selectedIndex = selectedIndex.value,
                    onItemSelected = { selectedIndex.value = it }
                )
            }

            selectedIndex.value?.let { index ->
                when (chips[index].first) {

                    "Seasons" -> {
                        items(state.tvShowDetailsUiState.seasons.size) { seasonIndex ->
                            val season = state.tvShowDetailsUiState.seasons[seasonIndex]
                            val isExpanded = expandedStates.value[seasonIndex]

                            SeasonHeader(
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

                    "Reviews" -> item {
                        ReviewsSection(
                            reviews = state.tvShowDetailsUiState.reviews.takeIf { it.isNotEmpty() }
                        )
                    }

                    "Gallery" -> item {
                        GallerySection(state.tvShowDetailsUiState.gallery)
                    }

                    "Company Production" -> item {
                        ProductionCompanySection(
                            companies = state.tvShowDetailsUiState.tvShowUi.productionCompanies
                        )
                    }

                    "More like this" -> item {
                        MoreLikeThisSection(
                            mediaList = listOf(
                                state.tvShowDetailsUiState.tvShowUi,
                                state.tvShowDetailsUiState.tvShowUi.copy(title = "Another TvShow")
                            ),
                            onClick = {},
                            mediaType = "TvShow"
                        )
                    }
                }
            }
        }

        TopAppBar(
            leadingIcons = listOf(
                iconItemWithDefaults(
                    icon = ImageVector.vectorResource(RDesignSystem.drawable.ic_back),
                    onClick = {}
                )
            ),
            trailingIcons = listOf(
                iconItemWithDefaults(
                    icon = ImageVector.vectorResource(R.drawable.ic_star),
                    onClick = {}
                ),
                iconItemWithDefaults(
                    icon = ImageVector.vectorResource(R.drawable.ic_heart_add),
                    onClick = {}
                )
            )
        )
    }
}

@PreviewLightDark
@Composable
fun CompanyProductionCardPreview() {
    AflamiTheme {
        TvShowDetailsScreenContent(
            state = fakeTvShowDetailsScreenState()
        )
    }
}


fun fakeTvShowDetailsScreenState(): TvShowDetailsScreenState {
    return TvShowDetailsScreenState(
        tvShowDetailsUiState = TvShowDetailsUiState(
            tvShowUi = TvShowUi(
                posterUrl = "https://upload.wikimedia.org/wikipedia/en/6/65/Breaking_Bad_title_card.png",
                rating = "9.5",
                title = "Breaking Bad",
                genres = listOf("Crime", "Drama", "Thriller"),
                releaseDate = "2008-01-20",
                runtime = "47m",
                country = "USA",
                description = "A high school chemistry teacher turned methamphetamine producer navigates the criminal underworld.",
                productionCompanies = listOf(
                    ProductionCompanyUi(
                        logoUrl = "https://upload.wikimedia.org/wikipedia/commons/1/1b/Sony_Pictures_Television_2014.svg",
                        name = "Sony Pictures Television",
                        originCountry = "US"
                    ),
                    ProductionCompanyUi(
                        logoUrl = "https://upload.wikimedia.org/wikipedia/commons/a/a2/AMC_logo_2013.svg",
                        name = "AMC",
                        originCountry = "US"
                    )
                )
            ),
            cast = listOf(
                CastUi(
                    name = "Bryan Cranston",
                    imageUrl = "https://upload.wikimedia.org/wikipedia/commons/2/2f/Bryan_Cranston_2012.jpg"
                ),
                CastUi(
                    name = "Aaron Paul",
                    imageUrl = "https://upload.wikimedia.org/wikipedia/commons/d/d7/Aaron_Paul_2014.jpg"
                )
            ),
            reviews = listOf(
                ReviewUi(
                    avatarUrl = "https://example.com/avatar1.jpg",
                    username = "walterwhitefan",
                    name = "Heisenberg",
                    rating = 10.0,
                    createdAt = "2013-09-30"
                ),
                ReviewUi(
                    avatarUrl = "https://example.com/avatar2.jpg",
                    username = "pinkmanpower",
                    name = "Jessie",
                    rating = 9.5,
                    createdAt = "2012-07-14"
                )
            ),
            gallery = listOf(
                "https://upload.wikimedia.org/wikipedia/en/6/65/Breaking_Bad_title_card.png",
                "https://upload.wikimedia.org/wikipedia/en/a/a3/Breaking_Bad_S5_Ep1.png",
                "https://upload.wikimedia.org/wikipedia/en/e/e6/Breaking_Bad_S5_Ep14.jpg"
            ),
            seasons = listOf(
                SeasonUi(
                    id = "1",
                    name = "Season 1",
                    episodes = listOf(
                        EpisodeUi(
                            episodeNumber = 1,
                            posterUrl = "",
                            voteAverage = 8.9,
                            airDate = "2008-01-20",
                            runtime = "58",
                            description = "Walter White, a chemistry teacher, is diagnosed with cancer and turns to making meth.",
                            stillUrl = ""
                        ),
                        EpisodeUi(
                            episodeNumber = 2,
                            posterUrl = "",
                            voteAverage = 8.7,
                            airDate = "2008-01-27",
                            runtime = "47",
                            description = "Walter and Jesse clean up the mess left behind.",
                            stillUrl = ""
                        )
                    )
                ),
                SeasonUi(
                    id = "2",
                    name = "Season 2",
                    episodes = listOf(
                        EpisodeUi(
                            episodeNumber = 1,
                            posterUrl = "",
                            voteAverage = 9.0,
                            airDate = "2009-03-08",
                            runtime = "47",
                            description = "Walter and Jesse expand their operation.",
                            stillUrl = ""
                        ),
                        EpisodeUi(
                            episodeNumber = 2,
                            posterUrl = "",
                            voteAverage = 9.1,
                            airDate = "2009-03-15",
                            runtime = "48",
                            description = "Problems arise as they scale production.",
                            stillUrl = ""
                        )
                    )
                )
            )
        ),
        isLoading = false,
        errorMessage = null
    )
}


