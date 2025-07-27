package com.feature.search.searchUi.screen.worldTour

import MediaUiState
import androidx.paging.PagingData
import com.domain.search.model.Country
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

data class WorldTourScreenState(
    val uiState: WorldTourUiState = WorldTourUiState(),
    val errorMessage: String? = null,
)

data class WorldTourUiState(
    val searchQuery: String = "",
    val searchResult: Flow<PagingData<MediaUiState>> = flowOf(PagingData.empty()),
    val hints: List<Country> = listOf(),
)