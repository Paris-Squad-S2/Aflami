package com.feature.search.searchUi.screen.findByActor

import com.feature.search.searchUi.screen.search.MediaTypeUi

interface FindByActorScreenInteractionListener {
    fun onNavigateBack()
    fun onSearchQueryChange(query: String)
    fun onMediaCardClick(id: Int, mediaType: MediaTypeUi)
    fun onRetrySearchQuery()
}