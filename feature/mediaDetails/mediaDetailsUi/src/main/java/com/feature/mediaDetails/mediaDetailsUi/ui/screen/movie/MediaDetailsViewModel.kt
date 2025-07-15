package com.feature.mediaDetails.mediaDetailsUi.ui.screen.movie

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.feature.mediaDetails.mediaDetailsApi.MediaDetailsDestinations
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.BaseViewModel

data class MovieDetailsViewModelScreenState(
    val uiState: String,
    val isLoading: Boolean,
    val errorMessage: String?
)

class MovieDetailsViewModelViewModel(
    savedStateHandle: SavedStateHandle
) :
    BaseViewModel<MovieDetailsViewModelScreenState>(
        MovieDetailsViewModelScreenState(
            uiState = "",
            isLoading = false,
            errorMessage = null
        )
    ) {

    init {
        val mediaId =
            savedStateHandle.toRoute<MediaDetailsDestinations.MovieDetailsScreen>().movieId
        emitState(
            MovieDetailsViewModelScreenState(
                uiState = "Movie ID: $mediaId",
                isLoading = false,
                errorMessage = null
            )
        )
    }

    fun onNavigate() {
        navigate(
            MediaDetailsDestinations.TvShowDetailsScreen(
                tvShowId = 123
            )
        )
    }
}