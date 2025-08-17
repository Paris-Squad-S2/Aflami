package com.feature.search.searchUi.screen.search

import androidx.paging.PagingData
import com.feature.search.searchUi.R
import com.paris_2.domain.media.entity.Category
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.datetime.LocalDate

data class SearchScreenState(
    val searchUiState: SearchUiState = SearchUiState(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)


data class SearchUiState(
    val searchQuery: String = "",
    val showFilterDialog: Boolean = false,
    val recentSearches: List<SearchHistoryUiState> = emptyList(),
    val selectedTabIndex: Int = 0,
    val moviesResult: Flow<PagingData<MediaUiState>> = emptyFlow(),
    val tvShowsResult: Flow<PagingData<MediaUiState>> = emptyFlow(),
    val filteredMoviesResult: Flow<PagingData<MediaUiState>> = emptyFlow(),
    val filteredTvShowsResult: Flow<PagingData<MediaUiState>> = emptyFlow(),
    val categories: Map<Category, Boolean> = emptyMap(),
    val selectedRating: Float = 0f,
    val isAllCategories: Boolean = true,
    val isApplyFilter: Boolean = false,
    val contentRestriction: ContentRestriction = ContentRestriction.Strict,
    val nsfwThreshold: Float = 0.8f,
    val genderThreshold: Float = 0.6f
)


data class MediaUiState(
    val id: Int,
    val imageUri: String,
    val title: String,
    val type: MediaTypeUi,
    val categories: List<Category>,
    val yearOfRelease: LocalDate,
    val rating: Double?,
)

enum class MediaTypeUi(val mediaName: String) {
    TvShow("TV Show"),
    Movie("Movie")
}

data class SearchHistoryUiState(
    val searchTitle: String,
    val searchDate: String,
    val searchType: SearchTypeUi
)

enum class SearchTypeUi(val displayNameResId: Int) {
    Query(R.string.query),
    Country(R.string.country),
    Actor(R.string.actor);
}

enum class ContentRestriction() {
    Strict,
    Moderate,
    Off
}
