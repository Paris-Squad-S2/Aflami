package com.feature.home.homeUi.screen.topRatingMovies

import com.feature.home.homeUi.common.ContentRestriction
import com.feature.home.homeUi.screen.home.MediaUiState

data class TopRatingMoviesUiState(
    val topRatingMovies: List<MediaUiState>,
    val isLoading: Boolean,
    val errorMessage: String?,
    val contentRestriction: ContentRestriction = ContentRestriction.Strict,
    val nsfwThreshold: Float = 0.8f,
    val genderThreshold: Float = 0.6f
)