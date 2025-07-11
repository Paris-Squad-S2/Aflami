package com.feature.search.searchUi.screen.findByActor

interface FindByActorScreenInteractionListener {
    fun onNavigateBack()
    fun onSearchQueryChange(query: String)
    fun onMediaCardClick(id: Int)
    fun onRetrySearchQuery()
}