package com.feature.mediaDetails.mediaDetailsUi.ui.screen.movie.cast

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.domain.mediaDetails.model.Cast
import com.domain.mediaDetails.useCase.movie.GetMovieCastUseCase
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.BaseViewModel
import com.feature.mediaDetails.mediaDetailsUi.ui.mapper.toListOfCastUi
import com.feature.mediaDetails.mediaDetailsUi.ui.navigation.MediaDetailsDestinations

class MovieCastViewModel
    (
    savedStateHandle: SavedStateHandle,
    private val getMovieCastUseCase: GetMovieCastUseCase,
) : MovieCastScreenInteractionListener,
    BaseViewModel<MovieCastUiState>(
        MovieCastUiState(
            cast = emptyList(),
            isLoading = false,
            errorMessage = null
        ),
    ) {
    private val mediaId by lazy {
        savedStateHandle.toRoute<MediaDetailsDestinations.MovieCastScreen>().movieId
    }

    init {
        loadMovieCast(mediaId)
    }

    override fun onNavigateBack() {
        navigateUp()
    }

    override fun onRetryLoadCast() {
        loadMovieCast(mediaId)
    }

    private fun loadMovieCast(mediaId: Int) {
        setMovieCastLoadingState()
        tryToExecute(
            execute = { getMovieCastUseCase(mediaId) },
            onSuccess = ::handleCastSuccess,
            onError = ::handleCastError
        )

    }

    private fun setMovieCastLoadingState() {
        updateState(
            screenState.value.copy(
                isLoading = true,
                errorMessage = null
            )
        )
    }

    private fun handleCastSuccess(castList: List<Cast>) {
        updateState(
            screenState.value.copy(
                cast = castList.toListOfCastUi(),
                isLoading = false,
                errorMessage = null
            )
        )
    }

    private fun handleCastError(error: String?) {
        updateState(
            screenState.value.copy(
                isLoading = false,
                errorMessage = error
            )
        )
    }
}