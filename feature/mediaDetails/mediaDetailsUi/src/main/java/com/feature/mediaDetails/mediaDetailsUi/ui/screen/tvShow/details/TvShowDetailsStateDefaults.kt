package com.feature.mediaDetails.mediaDetailsUi.ui.screen.tvShow.details

import androidx.paging.PagingData
import kotlinx.coroutines.flow.flowOf

object TvShowDetailsStateDefaults {
    fun initialTvShowDetailsScreenState(): TvShowDetailsScreenState {
        return TvShowDetailsScreenState(
            tvShowDetailsUiState = emptyTvShowDetailsUiState(),
            isLoading = true,
            errorMessage = null,
            isEpisodesLoading = true,
            seasonsLoadingStates = emptyMap()
        )
    }

    fun emptyTvShowDetailsUiState(): TvShowDetailsUiState {
        return TvShowDetailsUiState(
            tvShowUi = emptyTvShowUi(),
            cast = emptyList(),
            reviews = flowOf(PagingData.empty()),
            gallery = emptyList(),
            recommendations = flowOf(PagingData.empty()),
            tvShowVideoUi = emptyTvShowVideoUi()
        )
    }

    fun emptyTvShowUi(): TvShowUi {
        return TvShowUi(
            id = 0,
            posterUrl = "",
            rating = 0f,
            title = "",
            genres = emptyList(),
            releaseDate = "",
            runtime = "",
            country = "",
            description = "",
            seasons = emptyList(),
            productionCompanies = emptyList()
        )
    }

    fun emptyTvShowVideoUi(): TvShowVideoUi {
        return TvShowVideoUi(
            key = "",
            name = "",
            site = ""
        )
    }
}