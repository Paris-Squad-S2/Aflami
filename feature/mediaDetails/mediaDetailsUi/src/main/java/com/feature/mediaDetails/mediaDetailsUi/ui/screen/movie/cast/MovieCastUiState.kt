package com.feature.mediaDetails.mediaDetailsUi.ui.screen.movie.cast

import com.feature.mediaDetails.mediaDetailsUi.ui.screen.movie.details.CastUi

data class MovieCastUiState(
    val cast: List<CastUi>,
    val isLoading: Boolean,
    val errorMessage: String?
)
