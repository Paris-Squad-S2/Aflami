package com.feature.mediaDetails.mediaDetailsUi.ui.screen.tvShow.cast

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.domain.mediaDetails.useCases.tvShows.GetTvShowCastUseCase
import com.feature.mediaDetails.mediaDetailsApi.MediaDetailsDestinations
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.BaseViewModel
import com.feature.mediaDetails.mediaDetailsUi.ui.mapper.toListOfCastUi

class TvShowCastViewModel(
    savedStateHandle: SavedStateHandle,
    private val getTvShowCastUseCase: GetTvShowCastUseCase,
) : TvShowCastScreenInteractionListener, BaseViewModel<TvShowCastUiState>(
    TvShowCastUiState(
        cast = emptyList(),
        isLoading = false,
        errorMessage = null
    )
) {
    override fun onNavigateBack() {
        navigateUp()
    }

    init {
        val mediaId = savedStateHandle
            .toRoute<MediaDetailsDestinations.TvShowCastScreen>()
            .tvShowId
        loadTvShowCast(mediaId)
    }

    private fun loadTvShowCast(mediaId: Int) {
        updateState(screenState.value.copy(isLoading = true))

        tryToExecute(
            execute = { getTvShowCastUseCase(mediaId) },
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