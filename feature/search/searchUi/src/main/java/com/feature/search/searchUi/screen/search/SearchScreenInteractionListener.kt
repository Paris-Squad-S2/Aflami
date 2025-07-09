package com.feature.search.searchUi.screen.search

interface SearchScreenInteractionListener {
    fun onNavigateToWorldTourScreen()
    fun onNavigateToFindByActorScreen()
    fun onNavigateBack()
    fun onSearchQueryChange(query: String)
    fun onFilterButtonClick()
    fun onClearAllRecentSearches()
    fun onClearRecentSearch(id: Long)
    fun onRetryRecentSearches()
    fun onRetrySearchQuery()
    fun onMediaCardClick(id: Int)
    fun onSelectTab(tabIndex: Int)
}