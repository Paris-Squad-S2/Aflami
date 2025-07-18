package com.feature.search.searchUi.screen.search

interface SearchScreenInteractionListener {
    fun onNavigateToWorldTourScreen()
    fun onNavigateToFindByActorScreen()
    fun onNavigateBack()
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
        selectedCategories: List<CategoryUiState>
    )

    fun onRecentSearchClick(searchTitle: String, searchTypeUi: SearchTypeUi)
}