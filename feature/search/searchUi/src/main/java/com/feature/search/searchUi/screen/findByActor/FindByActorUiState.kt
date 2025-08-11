package com.feature.search.searchUi.screen.findByActor

import androidx.paging.PagingData
import com.feature.search.searchUi.screen.search.ContentRestriction
import com.feature.search.searchUi.screen.search.MediaUiState
import kotlinx.coroutines.flow.Flow

data class FindByActorScreenState(
    val uiState: FindByActorUiState,
    val errorMessage: String?
)

data class FindByActorUiState(
    val searchQuery: String,
    val searchResult: Flow<PagingData<MediaUiState>>,
    val contentRestriction: ContentRestriction = ContentRestriction.Strict,
    val nsfwThreshold: Float = 0.8f,
    val genderThreshold: Float = 0.6f
)
