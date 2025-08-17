package com.feature.mediaDetails.mediaDetailsUi.ui.screen.movie.cast

import com.feature.mediaDetails.mediaDetailsUi.ui.screen.movie.details.CastUi

data class MovieCastUiState(
    val cast: List<CastUi> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
