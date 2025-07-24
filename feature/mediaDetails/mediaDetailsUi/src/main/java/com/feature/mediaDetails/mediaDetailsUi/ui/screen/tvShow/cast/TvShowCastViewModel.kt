package com.feature.mediaDetails.mediaDetailsUi.ui.screen.tvShow.cast

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.domain.mediaDetails.model.Cast
import com.domain.mediaDetails.useCase.tvShows.GetTvShowCastUseCase
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.BaseViewModel
import com.feature.mediaDetails.mediaDetailsUi.ui.mapper.toListOfCastUi
import com.feature.mediaDetails.mediaDetailsUi.ui.navigation.MediaDetailsDestinations
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.tvShow.cast.TvShowCastDefaults.initialTvShowCastUiState

class TvShowCastViewModel(
    savedStateHandle: SavedStateHandle,
    private val getTvShowCastUseCase: GetTvShowCastUseCase,
) : TvShowCastScreenInteractionListener, BaseViewModel<TvShowCastUiState>(
    initialTvShowCastUiState()
) {
    private val mediaId by lazy {
        savedStateHandle.toRoute<MediaDetailsDestinations.TvShowCastScreen>().tvShowId
    }

    init {
        loadTvShowCast(mediaId)
    }

    override fun onNavigateBack() {
        navigateUp()
    }

    override fun onRetryLoadCast() {
        loadTvShowCast(mediaId)
    }

    private fun loadTvShowCast(mediaId: Int) {
        setTvShowCastLoadingState()
        tryToExecute(
            execute = { getTvShowCastUseCase(mediaId) },
            onSuccess = ::handleTvShowCastSuccess,
            onError = ::handleTvShowCastError
        )
    }

    private fun setTvShowCastLoadingState() {
        updateState(
            screenState.value.copy(
                isLoading = true,
                errorMessage = null
            )
        )
    }

    private fun handleTvShowCastSuccess(castList: List<Cast>) {
        updateState(
            screenState.value.copy(
                cast = castList.toListOfCastUi(),
                isLoading = false,
                errorMessage = null
            )
        )
    }

    private fun handleTvShowCastError(error: String?) {
        updateState(
            screenState.value.copy(
                isLoading = false,
                errorMessage = error
            )
        )
    }


}