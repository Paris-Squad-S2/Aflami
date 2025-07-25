package com.feature.home.homeUi.screen.topRatingMovies

import com.feature.home.homeUi.screen.home.MediaUiState

data class TopRatingMoviesUiState(
    val topRatingMovies: List<MediaUiState>,
    val isLoading: Boolean,
    val errorMessage: String?,
)