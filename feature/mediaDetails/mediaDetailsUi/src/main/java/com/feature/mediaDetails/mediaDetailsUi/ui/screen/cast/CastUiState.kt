package com.feature.mediaDetails.mediaDetailsUi.ui.screen.cast

import com.feature.mediaDetails.mediaDetailsUi.ui.screen.movie.CastUi

data class CastUiState(
    val cast: List<CastUi>,
    val isLoading: Boolean,
    val errorMessage: String?
)