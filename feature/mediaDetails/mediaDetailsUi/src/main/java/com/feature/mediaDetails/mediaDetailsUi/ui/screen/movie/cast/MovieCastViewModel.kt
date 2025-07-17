package com.feature.mediaDetails.mediaDetailsUi.ui.screen.movie.cast

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.feature.mediaDetails.mediaDetailsApi.MediaDetailsDestinations
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.BaseViewModel
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.movie.details.CastUi

class MovieCastViewModel
    (
    savedStateHandle: SavedStateHandle
) : MovieCastScreenInteractionListener,
    BaseViewModel<MovieCastUiState>(
    MovieCastUiState(
        cast = emptyList(),
        isLoading = false,
        errorMessage = null
    ),

) {
    override fun onNavigateBack() {
        navigateUp()
    }

    init {
        val mediaId = savedStateHandle
            .toRoute<MediaDetailsDestinations.MovieCastScreen>()
            .movieId

        emitState(
            MovieCastUiState(
                cast = listOf(
                    CastUi("Tom Hanks", "https://example.com/tom.jpg"),
                    CastUi("Michael Clarke Duncan", "https://example.com/michael.jpg")
                ),
                isLoading = false,
                errorMessage = null
            )
        )
    }


}