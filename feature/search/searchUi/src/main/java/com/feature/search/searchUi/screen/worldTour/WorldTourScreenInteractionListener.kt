package com.feature.search.searchUi.screen.worldTour

import MediaUiState


interface WorldTourScreenInteractionListener {
    fun onNavigateBack()
    fun onSearchQueryChange(query: String)
    fun onMediaCardClick(media: MediaUiState)
    fun onRetrySearchQuery()
}