package com.feature.profile.profileUi.screen.myRating

import com.feature.profile.profileUi.screen.watchHistory.MediaUiState

data class MyRatingUiState(
    val myRatingMedia: List<MediaUiState>,
    val isLoading: Boolean,
    val errorMessage: String?,
)
