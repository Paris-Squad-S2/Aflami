package com.feature.mediaDetails.mediaDetailsUi.ui.screen.tvShow.cast

import com.feature.mediaDetails.mediaDetailsUi.ui.screen.movie.details.CastUi

data class TvShowCastUiState(
    val cast: List<CastUi> = emptyList(),
    val isLoading: Boolean = true,
    val errorMessage: String? = null,
)