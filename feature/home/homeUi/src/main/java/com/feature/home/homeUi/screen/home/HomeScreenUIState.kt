package com.feature.home.homeUi.screen.home

import kotlinx.datetime.LocalDate

data class HomeScreenUIState(
    val homeUIState: HomeUIState,
    val isLoading: Boolean,
    val errorMessage: String?
)

data class HomeUIState(
    val popularMediaList: List<MediaUiState>,
    val continueWatchingMediaList: List<MediaUiState>,
    val topRatedMediaList: List<MediaUiState>,
    val moviesBirthdayMediaList: List<MediaUiState>,
    val categories: Map<CategoryUiState, Boolean>,
    val upComingMediaList: List<MediaUiState>,
    val showMoodPickerDialog : Boolean
    )

data class MediaUiState(
    val id: Int,
    val imageUri: String,
    val title: String,
    val type: MediaTypeUi,
    val categories: List<String>,
    val yearOfRelease: LocalDate,
    val rating: Double,
)

data class CategoryUiState(
    val id: Int,
    val name: String,
)


enum class MediaTypeUi(val mediaName: String) {
    TVSHOW("TV Show"),
    MOVIE("Movie")
}