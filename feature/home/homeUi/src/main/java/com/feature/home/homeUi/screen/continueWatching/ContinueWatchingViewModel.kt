package com.feature.home.homeUi.screen.continueWatching

import com.domain.home.usecase.GetMediaFromLocalUseCase
import com.feature.home.homeUi.common.BaseViewModel
import com.feature.home.homeUi.mapper.toMediaUiStateList

class ContinueWatchingViewModel(
    private val getMediaFromLocalUseCase: GetMediaFromLocalUseCase,
):BaseViewModel<ContinueWatchingUiState>(
    ContinueWatchingUiState(
        continueWatchingMediaList = emptyList(),
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
                getMediaFromLocalUseCase.invoke()
                      },
            onSuccess = { mediaList ->
                emitState(
                    screenState.value.copy(
                        continueWatchingMediaList = mediaList.toMediaUiStateList(),
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