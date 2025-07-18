package com.feature.mediaDetails.mediaDetailsUi.ui.screen.movie.cast

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.domain.mediaDetails.useCases.movie.GetMovieCastUseCase
import com.feature.mediaDetails.mediaDetailsApi.MediaDetailsDestinations
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.BaseViewModel
import com.feature.mediaDetails.mediaDetailsUi.ui.mapper.toListOfCastUi

class MovieCastViewModel
    (
    savedStateHandle: SavedStateHandle,
    private val getMovieCastUseCase: GetMovieCastUseCase
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
        loadMovieCast(mediaId)
    }

    private fun loadMovieCast(mediaId: Int) {
        updateState(screenState.value.copy(isLoading = true))
        tryToExecute(
            execute = { getMovieCastUseCase(mediaId) },
            onSuccess = { castList ->
                updateState(
                    screenState.value.copy(
                        cast = castList.toListOfCastUi(),
                        isLoading = false,
                        errorMessage = null
                    )
                )
            },
            onError = { error ->
                updateState(
                    screenState.value.copy(
                        isLoading = false,
                        errorMessage = error
                    )
                )
            }
        )
    }
}