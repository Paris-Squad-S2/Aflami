package com.feature.mediaDetails.mediaDetailsUi.ui.screen.tvShow.cast

object TvShowCastDefaults {
    fun initialTvShowCastUiState(): TvShowCastUiState {
        return TvShowCastUiState(
            cast = emptyList(),
            isLoading = false,
            errorMessage = null
        )
    }
}