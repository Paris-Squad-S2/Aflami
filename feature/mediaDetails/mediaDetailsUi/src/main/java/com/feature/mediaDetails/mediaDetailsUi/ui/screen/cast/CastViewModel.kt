package com.feature.mediaDetails.mediaDetailsUi.ui.screen.cast

import androidx.lifecycle.SavedStateHandle
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.BaseViewModel
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.movie.CastUi
import com.feature.mediaDetails.mediaDetailsApi.MediaDetailsDestinations
import com.feature.mediaDetails.mediaDetailsApi.fromJsonToMediaDetailsDestination


class CastViewModel(
    savedStateHandle: SavedStateHandle
)  : BaseViewModel<CastUiState>(
    CastUiState(
        cast = emptyList(),
        isLoading = false,
        errorMessage = null
    )
) {
    init {
        val destinationJson = checkNotNull(savedStateHandle["destination"]) as String
        val destination = destinationJson.fromJsonToMediaDetailsDestination()

        val mediaId = when (destination) {
            is MediaDetailsDestinations.MovieDetailsScreen -> destination.movieId
            is MediaDetailsDestinations.TvShowDetailsScreen -> destination.tvShowId
            is MediaDetailsDestinations.CastScreen -> TODO()
        }

        emitState(
            CastUiState(
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