package com.feature.home.homeUi.screen.topRatingMovies

import com.domain.home.usecase.GetTopRatingMediaUseCase
import com.feature.home.homeUi.common.BaseViewModel
import com.feature.home.homeUi.mapper.toMediaUiStateList

class TopRatingMoviesViewModel(
    private val getTopRatingMediaUseCase: GetTopRatingMediaUseCase
):BaseViewModel<TopRatingMoviesUiState>(
    TopRatingMoviesUiState(
        topRatingMovies = emptyList(),
        isLoading = false,
        errorMessage = null
    )
) {

    init {
        loadContinueWatchingMedia()
    }

    private fun loadContinueWatchingMedia() {
        tryToExecute(
            execute = {
                emitState(
                    screenState.value.copy(
                        isLoading = true
                    )
                )
                getTopRatingMediaUseCase.invoke()
                      },
            onSuccess = { mediaList ->
                emitState(
                    screenState.value.copy(
                        topRatingMovies = mediaList.toMediaUiStateList(),
                        isLoading = false
                    )
                )},
            onError = {error ->
                emitState(
                    screenState.value.copy(
                        errorMessage = error,
                        isLoading = false
                    )
                )
            }
        )
    }

}