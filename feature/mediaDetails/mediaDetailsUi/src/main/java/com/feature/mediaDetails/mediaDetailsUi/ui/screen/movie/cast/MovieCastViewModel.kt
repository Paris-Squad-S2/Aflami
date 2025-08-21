package com.feature.mediaDetails.mediaDetailsUi.ui.screen.movie.cast

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.BaseViewModel
import com.feature.mediaDetails.mediaDetailsUi.ui.mapper.toListOfCastUi
import com.feature.mediaDetails.mediaDetailsUi.ui.navigation.MediaDetailsDestinations
import com.feature.mediaDetails.mediaDetailsUi.ui.navigation.MediaDetailsNavigator
import com.paris.domain.media.useCase.movie.GetMovieCastUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieCastViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getMovieCastUseCase: GetMovieCastUseCase,
    navigator: MediaDetailsNavigator
) : MovieCastScreenInteractionListener,
    BaseViewModel<MovieCastUiState>(
        MovieCastUiState(), navigator
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
        updateState(screenState.value.copy(isLoading = true))
        tryToExecute(
            execute = { getMovieCastUseCase(mediaId) },
            onSuccess = ::onLoadMovieCastSuccess,
            onError = ::onLoadMovieCastError
        )
    }
    
    private fun onLoadMovieCastSuccess(castList: List<com.paris.domain.media.entity.Cast>) {
        updateState(
            screenState.value.copy(
                cast = castList.toListOfCastUi(),
                isLoading = false,
                errorMessage = null
            )
        )
    }
    
    private fun onLoadMovieCastError(error: String) {
        updateState(
            screenState.value.copy(
                isLoading = false,
                errorMessage = error
            )
        )
    }

}