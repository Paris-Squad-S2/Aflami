package com.feature.search.searchUi.screen.findByActor

import MediaUiState
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

data class FindByActorScreenState(
    val uiState: FindByActorUiState,
    val errorMessage: String?
)

data class FindByActorUiState(
    val searchQuery: String,
    val searchResult: Flow<PagingData<MediaUiState>>,
)
