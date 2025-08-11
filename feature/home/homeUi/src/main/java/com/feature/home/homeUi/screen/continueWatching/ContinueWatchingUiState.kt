package com.feature.home.homeUi.screen.continueWatching

import com.feature.home.homeUi.common.ContentRestriction
import com.feature.home.homeUi.screen.home.MediaUiState

data class ContinueWatchingUiState(
    val continueWatchingMediaList: List<MediaUiState>,
    val isLoading: Boolean,
    val errorMessage: String?,
    val contentRestriction: ContentRestriction = ContentRestriction.STRICT,
    val nsfwThreshold: Float = 0.8f,
    val genderThreshold: Float = 0.6f
)