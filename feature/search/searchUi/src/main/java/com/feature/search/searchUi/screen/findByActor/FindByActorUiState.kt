package com.feature.search.searchUi.screen.findByActor

import androidx.paging.PagingData
import com.feature.search.searchUi.screen.search.ContentRestriction
import com.feature.search.searchUi.screen.search.MediaUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class FindByActorScreenState(
    val uiState: FindByActorUiState = FindByActorUiState(),
    val errorMessage: String? = null
)

data class FindByActorUiState(
    val searchQuery: String = "",
    val searchResult: Flow<PagingData<MediaUiState>> = emptyFlow(),
    val contentRestriction: ContentRestriction = ContentRestriction.Strict,
    val nsfwThreshold: Float = 0.8f,
    val genderThreshold: Float = 0.6f
)
