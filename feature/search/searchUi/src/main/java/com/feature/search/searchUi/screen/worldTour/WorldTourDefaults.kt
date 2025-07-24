package com.feature.search.searchUi.screen.worldTour

import androidx.paging.PagingData
import kotlinx.coroutines.flow.flowOf

object WorldTourDefaults {
    fun initialWorldTourScreenStata(): WorldTourScreenState{
        return WorldTourScreenState(
            uiState = WorldTourUiState(
                searchQuery = "",
                searchResult = flowOf(PagingData.empty()),
                hints = listOf()
            ),
            errorMessage = null
        )
    }

}