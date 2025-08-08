package com.feature.profile.profileUi.screen.watchHistory

import kotlinx.datetime.LocalDate

data class WatchHistoryUiState(
    val watchHistoryMedia: List<MediaUiState>,
    val isLoading: Boolean,
    val errorMessage: String?,
)

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