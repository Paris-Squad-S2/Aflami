package com.feature.search.searchUi.screen.worldTour

import MediaUiState
import androidx.paging.PagingData
import com.domain.search.model.Country
import kotlinx.coroutines.flow.Flow

data class WorldTourScreenState(
    val uiState: WorldTourUiState,
    val errorMessage: String?
)

data class WorldTourUiState(
    val searchQuery: String,
    val searchResult: Flow<PagingData<MediaUiState>>,
    val hints: List<Country>
)