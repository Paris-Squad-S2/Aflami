package com.feature.search.searchUi.screen.worldTour

import androidx.paging.PagingData
import com.feature.search.searchUi.screen.search.ContentRestriction
import com.feature.search.searchUi.screen.search.MediaUiState
import com.paris_2.domain.media.entity.Country
import kotlinx.coroutines.flow.Flow

data class WorldTourScreenState(
    val uiState: WorldTourUiState,
    val errorMessage: String?
)

data class WorldTourUiState(
    val searchQuery: String,
    val searchResult: Flow<PagingData<MediaUiState>>,
    val hints: List<Country>,
    val contentRestriction: ContentRestriction = ContentRestriction.Strict,
    val nsfwThreshold: Float = 0.8f,
    val genderThreshold: Float = 0.6f
)