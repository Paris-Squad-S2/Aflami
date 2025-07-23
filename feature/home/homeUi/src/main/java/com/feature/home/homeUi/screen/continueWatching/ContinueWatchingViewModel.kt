package com.feature.home.homeUi.screen.continueWatching

import com.feature.home.homeUi.common.BaseViewModel
import com.feature.home.homeUi.fake.FakeContinueWatchingUseCase

class ContinueWatchingViewModel(
    private val fakeContinueWatchingUseCase: FakeContinueWatchingUseCase,
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
                fakeContinueWatchingUseCase.invoke()
                      },
            onSuccess = { mediaList ->
                emitState(
                    screenState.value.copy(
                        continueWatchingMediaList = mediaList,
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