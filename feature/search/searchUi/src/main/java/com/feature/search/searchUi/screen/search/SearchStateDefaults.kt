package com.feature.search.searchUi.screen.search

import SearchScreenState
import SearchUiState
import androidx.paging.PagingData
import kotlinx.coroutines.flow.flowOf

object SearchStateDefaults {

    fun initialSearchScreenState(): SearchScreenState {
        return SearchScreenState(
            searchUiState = SearchUiState(
                searchQuery = "",
                showFilterDialog = false,
                recentSearches = listOf(),
                filteredMoviesResult = flowOf(PagingData.empty()),
                filteredTvShowsResult = flowOf(PagingData.empty()),
                selectedTabIndex = 0,
                categories = mapOf(),
                selectedRating = 0f,
                moviesResult = flowOf(PagingData.empty()),
                tvShowsResult = flowOf(PagingData.empty()),
                isAllCategories = true,
                isApplyFilter = false
            ),
            isLoading = false,
            errorMessage = null
        )
    }
}