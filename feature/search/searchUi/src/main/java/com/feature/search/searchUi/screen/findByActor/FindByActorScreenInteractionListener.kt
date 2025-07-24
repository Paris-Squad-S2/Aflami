package com.feature.search.searchUi.screen.findByActor

import MediaUiState


interface FindByActorScreenInteractionListener {
    fun onNavigateBack()
    fun onSearchQueryChange(query: String)
    fun onMediaCardClick(media: MediaUiState)
    fun onRetrySearchQuery()
}