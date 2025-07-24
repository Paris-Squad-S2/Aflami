package com.feature.home.homeUi.screen.continueWatching

import com.domain.home.usecase.GetMediaFromLocalUseCase
import com.feature.home.homeUi.common.BaseViewModel
import com.feature.home.homeUi.mapper.toMediaUiStateList
import com.feature.home.homeUi.screen.home.MediaTypeUi
import com.feature.home.homeUi.screen.home.MediaUiState
import com.feature.mediaDetails.mediaDetailsApi.MediaDetailsDestinations
import com.feature.mediaDetails.mediaDetailsApi.toJson
import com.paris_2.aflami.appnavigation.AppDestinations
import com.paris_2.aflami.appnavigation.AppNavigator

class ContinueWatchingViewModel(
    private val getMediaFromLocalUseCase: GetMediaFromLocalUseCase,
    private val appNavigator: AppNavigator
):BaseViewModel<ContinueWatchingUiState>(
    ContinueWatchingUiState(
        continueWatchingMediaList = emptyList(),
        isLoading = false,
        errorMessage = null
    )
), ContinueWatchingInteractionListener {

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

    fun onRetry(){
        loadContinueWatchingMedia()
    }

    override fun onMediaCardClick(media: MediaUiState) {
        tryToExecute(
            execute = {
                appNavigator.navigate(
                    AppDestinations.MediaDetailsFeature(
                        when (media.type) {
                            MediaTypeUi.MOVIE -> MediaDetailsDestinations.MovieDetailsScreen(
                                movieId = media.id
                            )

                            MediaTypeUi.TVSHOW -> MediaDetailsDestinations.TvShowDetailsScreen(
                                tvShowId = media.id
                            )
                        }.toJson()
                    )
                )
            },
            onError = { errorMessage ->
                emitState(
                    screenState.value.copy(
                        errorMessage = errorMessage
                    )
                )
            }
        )
    }

    override fun onBackButtonClick() {
        tryToExecute(
            execute = { appNavigator.navigateUp() },
            onError = {
                emitState(
                    screenState.value.copy(
                        errorMessage = it
                    )
                )
            }
        )
    }

}