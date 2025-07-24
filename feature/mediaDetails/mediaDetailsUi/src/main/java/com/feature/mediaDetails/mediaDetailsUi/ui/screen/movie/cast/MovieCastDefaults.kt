package com.feature.mediaDetails.mediaDetailsUi.ui.screen.movie.cast

object MovieCastDefaults {

    fun initialMovieCastUiState(): MovieCastUiState {
        return MovieCastUiState(
            cast = emptyList(),
            isLoading = false,
            errorMessage = null
        )
    }
}