package com.feature.home.homeUi.screen.continueWatching

import com.feature.home.homeUi.screen.home.MediaUiState

data class ContinueWatchingUiState(
    val continueWatchingMediaList: List<MediaUiState>,
    val isLoading: Boolean,
    val errorMessage: String?,
)