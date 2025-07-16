package com.feature.mediaDetails.mediaDetailsUi.ui.screen.tvShow

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.feature.mediaDetails.mediaDetailsApi.MediaDetailsDestinations
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.BaseViewModel

data class MediaDetailsViewModelScreenState(
    val uiState: String,
    val isLoading: Boolean,
    val errorMessage: String?
)

class TvShowDetailsViewModelViewModel(
    savedStateHandle: SavedStateHandle
) :
    BaseViewModel<MediaDetailsViewModelScreenState>(
        MediaDetailsViewModelScreenState(
            uiState = "",
            isLoading = false,
            errorMessage = null
        )
    ) {

    init {
        val mediaId =
            savedStateHandle.toRoute<MediaDetailsDestinations.TvShowDetailsScreen>().tvShowId
        emitState(
            MediaDetailsViewModelScreenState(
                uiState = "TvShow ID: $mediaId",
                isLoading = false,
                errorMessage = null
            )
        )
    }

    fun onNavigate() {
        navigate(
            MediaDetailsDestinations.MovieDetailsScreen(
                movieId = 123
            )
        )
    }
}