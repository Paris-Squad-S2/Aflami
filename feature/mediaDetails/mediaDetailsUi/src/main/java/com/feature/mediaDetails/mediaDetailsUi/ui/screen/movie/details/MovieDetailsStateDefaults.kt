package com.feature.mediaDetails.mediaDetailsUi.ui.screen.movie.details

import androidx.paging.PagingData
import kotlinx.coroutines.flow.flowOf

object MovieDetailsStateDefaults {
     fun initialMovieDetailsScreenState(): MovieDetailsScreenState {
        return MovieDetailsScreenState(
            movieDetailsUiState = emptyMovieDetailsUiState(),
            isLoading = true,
            errorMessage = null
        )
    }

    private fun emptyMovieDetailsUiState() = MovieDetailsUiState(
        movie = emptyMovieUi(),
        cast = emptyList(),
        reviews = flowOf(PagingData.empty()),
        gallery = emptyList(),
        recommendations = flowOf(PagingData.empty()),
        movieVideoUi = emptyMovieVideoUi()
    )


    private fun emptyMovieUi() = MovieUi(
        id = 0,
        posterUrl = "",
        rating = 0f,
        title = "",
        genres = emptyList(),
        releaseDate = "",
        runtime = "",
        country = "",
        description = "",
        productionCompanies = emptyList(),
    )

    private fun emptyMovieVideoUi() = MovieVideoUi(
        key = "",
        name = "",
        site = "",
    )

}