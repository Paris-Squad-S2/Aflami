package com.feature.mediaDetails.mediaDetailsUi.screen.cast

import com.feature.mediaDetails.mediaDetailsUi.screen.movie.CastUi

data class CastUiState(
    val cast: List<CastUi>,
    val isLoading: Boolean,
    val errorMessage: String?
)
