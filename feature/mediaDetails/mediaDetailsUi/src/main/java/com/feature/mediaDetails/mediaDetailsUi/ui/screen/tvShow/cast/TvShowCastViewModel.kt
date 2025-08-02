package com.feature.mediaDetails.mediaDetailsUi.ui.screen.tvShow.cast

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.paris_2.domain.media.useCase.tvShows.GetTvShowCastUseCase
import com.feature.mediaDetails.mediaDetailsUi.ui.navigation.MediaDetailsDestinations
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.BaseViewModel
import com.feature.mediaDetails.mediaDetailsUi.ui.mapper.toListOfCastUi
import com.feature.mediaDetails.mediaDetailsUi.ui.navigation.MediaDetailsNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TvShowCastViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getTvShowCastUseCase: GetTvShowCastUseCase,
    navigator: MediaDetailsNavigator
) : TvShowCastScreenInteractionListener, BaseViewModel<TvShowCastUiState>(
    TvShowCastUiState(
        cast = emptyList(),
        isLoading = false,
        errorMessage = null
    ), navigator
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