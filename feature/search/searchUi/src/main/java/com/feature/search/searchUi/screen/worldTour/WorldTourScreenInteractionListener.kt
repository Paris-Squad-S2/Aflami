package com.feature.search.searchUi.screen.worldTour

import com.feature.search.searchUi.screen.search.MediaTypeUi

interface WorldTourScreenInteractionListener {
    fun onNavigateBack()
    fun onSearchQueryChange(query: String)
    fun onMediaCardClick(id: Int, mediaType: MediaTypeUi)
    fun onRetrySearchQuery()
}