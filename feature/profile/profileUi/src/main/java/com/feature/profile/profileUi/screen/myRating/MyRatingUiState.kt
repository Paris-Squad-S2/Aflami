package com.feature.profile.profileUi.screen.myRating

import com.feature.profile.profileUi.screen.watchHistory.MediaUiState

data class MyRatingUiState(
    val myRatingMedia: List<MediaUiState>,
    val isLoading: Boolean,
    val errorMessage: String?,
    val contentRestriction: ContentRestriction = ContentRestriction.STRICT,
    val nsfwThreshold: Float = 0.8f,
    val genderThreshold: Float = 0.6f
)

enum class ContentRestriction() {
    STRICT,
    MODERATE,
    OFF
}
