package com.feature.search.searchUi.screen.search

import com.domain.search.model.CategoryModel

interface SearchScreenInteractionListener {
    fun onNavigateToWorldTourScreen()
    fun onNavigateToFindByActorScreen()
    fun onNavigateBack()
    fun onSearchQueryChange(query: String)
    fun onFilterButtonClick()
    fun onClearFilterClick()
    fun onClearAllRecentSearches()
    fun onClearRecentSearch(id: String)
    fun onRetryRecentSearches()
    fun onRetrySearchQuery()
    fun onMediaCardClick(id: Int)
    fun onSelectTab(tabIndex: Int)
    fun onApplyFilterButtonClick(
        selectedRating: Float,
        selectedCategories: List<CategoryModel>
    )
}