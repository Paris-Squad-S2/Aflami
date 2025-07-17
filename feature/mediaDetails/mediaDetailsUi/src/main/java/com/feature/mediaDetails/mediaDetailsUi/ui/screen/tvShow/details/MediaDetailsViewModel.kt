package com.feature.mediaDetails.mediaDetailsUi.ui.screen.tvShow.details

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.feature.mediaDetails.mediaDetailsApi.MediaDetailsDestinations
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.BaseViewModel
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.movie.details.CastUi
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.movie.details.ProductionCompanyUi
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.movie.details.ReviewUi

class TvShowDetailsViewModelViewModel(
    savedStateHandle: SavedStateHandle,
) :
    BaseViewModel<TvShowDetailsScreenState>(
        TvShowDetailsScreenState(
            TvShowDetailsUiState(
                tvShowUi = TvShowUi(
                    posterUrl = "",
                    rating = "",
                    title = "",
                    genres = emptyList(),
                    releaseDate = "",
                    runtime = "",
                    country = "",
                    description = "",
                    productionCompanies = emptyList()
                ),
                cast = emptyList(),
                reviews = emptyList(),
                gallery = emptyList(),
                seasons = emptyList()
            ),
            isLoading = false,
            errorMessage = null
        )
    ) {

    init {
        val mediaId =
            savedStateHandle.toRoute<MediaDetailsDestinations.TvShowDetailsScreen>().tvShowId

        emitState(
            TvShowDetailsScreenState(
                tvShowDetailsUiState = TvShowDetailsUiState(
                    tvShowUi = TvShowUi(
                        posterUrl = "https://via.placeholder.com/300x450",
                        rating = "8.7",
                        title = "Mock Show $mediaId",
                        genres = listOf("Drama", "Sci-Fi"),
                        releaseDate = "2022-09-10",
                        runtime = "60",
                        country = "USA",
                        description = "This is a mocked TV show used for development. ID: $mediaId",
                        productionCompanies = listOf(
                            ProductionCompanyUi(
                                logoUrl = "https://via.placeholder.com/100",
                                name = "Mock Studio",
                                originCountry = "US"
                            )
                        )
                    ),
                    cast = listOf(
                        CastUi(name = "Mock Actor 1", imageUrl = "https://via.placeholder.com/150"),
                        CastUi(name = "Mock Actor 2", imageUrl = "https://via.placeholder.com/150")
                    ),
                    reviews = listOf(
                        ReviewUi(
                            avatarUrl = "https://via.placeholder.com/50",
                            username = "mockuser1",
                            name = "Mock Reviewer",
                            rating = 4.0,
                            createdAt = "2024-11-01"
                        )
                    ),
                    gallery = listOf(
                        "https://via.placeholder.com/300x200",
                        "https://via.placeholder.com/300x200"
                    ),
                    seasons = listOf(
                        SeasonUi(
                            id = "s1",
                            name = "Season 1",
                            episodes = listOf(
                                EpisodeUi(
                                    episodeNumber = 1,
                                    posterUrl = "https://via.placeholder.com/150",
                                    voteAverage = 7.9,
                                    airDate = "2022-09-10",
                                    runtime = "58",
                                    description = "Pilot episode with a thrilling mystery.",
                                    stillUrl = "https://via.placeholder.com/300"
                                ),
                                EpisodeUi(
                                    episodeNumber = 2,
                                    posterUrl = "https://via.placeholder.com/150",
                                    voteAverage = 8.2,
                                    airDate = "2022-09-17",
                                    runtime = "61",
                                    description = "Second episode continues the tension.",
                                    stillUrl = "https://via.placeholder.com/300"
                                )
                            )
                        )
                    )
                ),
                isLoading = false,
                errorMessage = null
            )
        )
    }

    fun onNavigate() {
        navigate(
            MediaDetailsDestinations.MovieDetailsScreen(
                movieId = 123
            )
        )
    }
}