package com.feature.search.searchUi.screen.worldTour

import androidx.paging.PagingData
import com.feature.search.searchUi.screen.search.ContentRestriction
import com.feature.search.searchUi.screen.search.MediaUiState
import com.paris.domain.media.entity.Country
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class WorldTourScreenState(
    val uiState: WorldTourUiState = WorldTourUiState(),
    val errorMessage: String? = null
)

data class WorldTourUiState(
    val searchQuery: String = "",
    val searchResult: Flow<PagingData<MediaUiState>> = emptyFlow(),
    val hints: List<Country> = emptyList(),
    val contentRestriction: ContentRestriction = ContentRestriction.Strict,
    val nsfwThreshold: Float = 0.8f,
    val genderThreshold: Float = 0.6f
)