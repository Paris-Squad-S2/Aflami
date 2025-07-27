package com.feature.search.searchUi.screen.findByActor

import MediaUiState
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

data class FindByActorScreenState(
    val uiState: FindByActorUiState = FindByActorUiState(),
    val errorMessage: String? =null
)

data class FindByActorUiState(
    val searchQuery: String ="",
    val searchResult: Flow<PagingData<MediaUiState>> = flowOf(PagingData.empty()),
)

