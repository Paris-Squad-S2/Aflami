package com.feature.profile.profileUi.screen.myRating

import com.feature.profile.profileUi.screen.watchHistory.MediaUiState

data class MyRatingUiState(
    val myRatingMedia: List<MediaUiState> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val contentRestriction: ContentRestriction = ContentRestriction.Strict,
    val nsfwThreshold: Float = 0.8f,
    val genderThreshold: Float = 0.6f
)

enum class ContentRestriction() {
    Strict,
    Moderate,
    Off
}
