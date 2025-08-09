package com.feature.search.searchUi.screen.search

import CategoryUiState
import MediaUiState
import SearchTypeUi
import com.paris_2.domain.media.entity.Category

interface SearchScreenInteractionListener {
    fun onNavigateToWorldTourScreen()
    fun onNavigateToFindByActorScreen()
    fun onSearchQueryChange(query: String)
    fun onFilterButtonClick()
    fun onClearFilterClick()
    fun onClearAllRecentSearches()
    fun onClearRecentSearch(id: String, searchTypeUi: SearchTypeUi)
    fun onRetryRecentSearches()
    fun onRetrySearchQuery()
    fun onMediaCardClick(mediaUiState: MediaUiState)
    fun onSelectTab(tabIndex: Int)
    fun onApplyFilterButtonClick(
        selectedRating: Float,
        isAllCategories: Boolean,
        selectedCategories: List<Category>
    )

    fun onRecentSearchClick(searchTitle: String, searchTypeUi: SearchTypeUi)
}