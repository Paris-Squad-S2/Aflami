package com.feature.mediaDetails.mediaDetailsUi.ui.screen.movie.details

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.feature.mediaDetails.mediaDetailsApi.MediaDetailsDestinations
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.BaseViewModel

class MovieDetailsViewModelViewModel(
    savedStateHandle: SavedStateHandle,
) :
    BaseViewModel<MovieDetailsScreenState>(
        MovieDetailsScreenState(
            movieDetailsUiState = MovieDetailsUiState(
                    movie = MovieUi(
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
                    gallery = emptyList()
                ),
                isLoading = false,
                errorMessage = null
            )
    ) {
    init {
        val mediaId =
            savedStateHandle.toRoute<MediaDetailsDestinations.MovieDetailsScreen>().movieId
        emitState(
            MovieDetailsScreenState(
                movieDetailsUiState = MovieDetailsUiState(
                    movie = MovieUi(
                        posterUrl = "https://image.tmdb.org/t/p/original/iaSA91XEY01hAcftOe9Vc9qfCNa.jpg",
                        rating = "8.2",
                        title = "Inception (ID: $mediaId)",
                        genres = listOf("Action", "Science Fiction", "Thriller"),
                        releaseDate = "2010-07-16",
                        runtime = "2h 28m",
                        country = "USA",
                        description = "A thief who steals corporate secrets through use of dream-sharing technology is given the inverse task of planting an idea into the mind of a CEO.",
                        productionCompanies = listOf(
                            ProductionCompanyUi(
                                logoUrl = "https://image.tmdb.org/t/p/original/iaSA91XEY01hAcftOe9Vc9qfCNa.jpg",
                                name = "Legendary Pictures",
                                originCountry = "USA"
                            ),
                            ProductionCompanyUi(
                                logoUrl = "https://image.tmdb.org/t/p/original/iaSA91XEY01hAcftOe9Vc9qfCNa.jpg",
                                name = "Syncopy",
                                originCountry = "UK"
                            )
                        )
                    ),
                    cast = listOf(
                        CastUi(
                            name = "Leonardo DiCaprio",
                            imageUrl = "https://image.tmdb.org/t/p/original/iaSA91XEY01hAcftOe9Vc9qfCNa.jpg"
                        ),
                        CastUi(
                            name = "Joseph Gordon-Levitt",
                            imageUrl = "https://image.tmdb.org/t/p/original/iaSA91XEY01hAcftOe9Vc9qfCNa.jpg"
                        )
                    ),
                    reviews = listOf(
                        ReviewUi(
                            avatarUrl = "https://image.tmdb.org/t/p/original/iaSA91XEY01hAcftOe9Vc9qfCNa.jpg",
                            username = "movie_buff_91",
                            name = "Amazing Movie!",
                            rating = 9.0,
                            createdAt = "2023-04-12"
                        ),
                        ReviewUi(
                            avatarUrl = "https://image.tmdb.org/t/p/original/iaSA91XEY01hAcftOe9Vc9qfCNa.jpg",
                            username = "cinema_fanatic",
                            name = "Mind-blowing!",
                            rating = 8.5,
                            createdAt = "2023-05-20"
                        )
                    ),
                    gallery = listOf(
                        "https://image.tmdb.org/t/p/original/iaSA91XEY01hAcftOe9Vc9qfCNa.jpg",
                        "https://image.tmdb.org/t/p/original/iaSA91XEY01hAcftOe9Vc9qfCNa.jpg"
                    )
                ),
                isLoading = false,
                errorMessage = null
            )
        )
    }

    fun onNavigate() {
        navigate(
            MediaDetailsDestinations.TvShowDetailsScreen(
                tvShowId = 123
            )
        )
    }
}