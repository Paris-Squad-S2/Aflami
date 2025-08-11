package com.feature.profile.profileUi.screen.watchHistory

import kotlinx.datetime.LocalDate

data class WatchHistoryUiState(
    val watchHistoryMedia: List<MediaUiState>,
    val isLoading: Boolean,
    val errorMessage: String?,
    val contentRestriction: ContentRestriction = ContentRestriction.Strict,
    val nsfwThreshold: Float = 0.8f,
    val genderThreshold: Float = 0.6f
)

enum class ContentRestriction() {
    Strict,
    Moderate,
    Off
}


data class MediaUiState(
    val id: Int,
    val imageUri: String,
    val rating: Double?,
    val title: String,
    val type: MediaTypeUi,
    val yearOfRelease: LocalDate
)



enum class MediaTypeUi(val mediaName: String) {
    TVSHOW("TV Show"),
    MOVIE("Movie")
}