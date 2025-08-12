package com.feature.home.homeUi.screen.home

import com.feature.home.homeUi.R
import com.feature.home.homeUi.screen.home.components.SliderMedia
import com.paris_2.domain.media.entity.Category
import kotlinx.datetime.LocalDate

data class HomeScreenUIState(
    val homeUIState: HomeUIState = HomeUIState(),
    val isPopularMediaLoading: Boolean = false,
    val isTopRatingLoading: Boolean = false,
    val isContinueWatchingLoading: Boolean = false,
    val isCategoryLoading: Boolean = false,
    val errorMessage: String? = null
)

data class HomeUIState(
    val popularMediaList: List<SliderMedia> = emptyList(),
    val continueWatchingMediaList: List<MediaUiState> = emptyList(),
    val topRatedMediaList: List<MediaUiState> = emptyList(),
    val moviesBirthdayMediaList: List<MediaUiState> = emptyList(),
    val categories: Map<Category, Boolean> = emptyMap(),
    val upComingMediaList: List<MediaUiState> = emptyList(),
    val showMoodPickerDialog: Boolean = false,
    val isAllCategories: Boolean = false,
    val moodPickerMovie: MediaUiState? = null
)

data class MediaUiState(
    val id: Int = 0,
    val imageUri: String = "",
    val title: String = "",
    val type: MediaTypeUi = MediaTypeUi.MOVIE,
    val categories: List<Int> = emptyList(),
    val yearOfRelease: LocalDate = LocalDate(1970, 1, 1),
    val rating: Double? = null
)

enum class MediaTypeUi(val mediaID: Int) {
    TVSHOW(R.string.tv_show),
    MOVIE(R.string.movie)
}