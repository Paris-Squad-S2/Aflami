package com.feature.home.homeUi.screen.home

import com.feature.home.homeUi.screen.home.components.SliderMedia
import com.paris_2.domain.media.entity.Category
import kotlinx.datetime.LocalDate

data class HomeScreenUIState(
    val homeUIState: HomeUIState,
    val isPopularMediaLoading: Boolean,
    val isTopRatingLoading: Boolean,
    val isContinueWatchingLoading: Boolean,
    val isCategoryLoading: Boolean,
    val errorMessage: String?
)

data class HomeUIState(
    val popularMediaList: List<SliderMedia>,
    val continueWatchingMediaList: List<MediaUiState>,
    val topRatedMediaList: List<MediaUiState>,
    val moviesBirthdayMediaList: List<MediaUiState>,
    val categories: Map<Category, Boolean>,
    val upComingMediaList: List<MediaUiState>,
    val showMoodPickerDialog: Boolean,
    val isAllCategories: Boolean,
    val moodPickerMovie: MediaUiState?
    )

data class MediaUiState(
    val id: Int,
    val imageUri: String,
    val title: String,
    val type: MediaTypeUi,
    val categories: List<String>,
    val yearOfRelease: LocalDate,
    val rating: Double?,
)



enum class MediaTypeUi(val mediaName: String) {
    TVSHOW("TV Show"),
    MOVIE("Movie")
}