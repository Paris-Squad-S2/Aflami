package com.feature.search.searchUi.screen.worldTour

interface WorldTourScreenInteractionListener {
    fun onNavigateBack()
    fun onSearchQueryChange(query: String)
    fun onMediaCardClick(id: Int)
    fun onRetrySearchQuery()
}