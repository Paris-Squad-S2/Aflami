package com.feature.search.searchUi.screen.findByActor

import androidx.paging.PagingData
import kotlinx.coroutines.flow.flowOf

object FindByActorDefaults {
    fun initialFindByActorScreenState(): FindByActorScreenState {
        return FindByActorScreenState(
            uiState = FindByActorUiState(
                searchQuery = "",
                searchResult = flowOf(PagingData.empty())
            ),
            errorMessage = null
        )
    }

}