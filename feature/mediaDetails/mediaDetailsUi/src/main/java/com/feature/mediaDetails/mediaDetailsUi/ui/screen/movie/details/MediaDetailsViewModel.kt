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
                        posterUrl = "",
                        rating = "",
                        title = "Movie ID: $mediaId",
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