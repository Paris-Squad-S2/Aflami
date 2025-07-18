package com.feature.search.searchUi.screen.worldTour

import com.feature.search.searchUi.screen.search.MediaUiState

interface WorldTourScreenInteractionListener {
    fun onNavigateBack()
    fun onSearchQueryChange(query: String)
    fun onMediaCardClick(media: MediaUiState)
    fun onRetrySearchQuery()
}