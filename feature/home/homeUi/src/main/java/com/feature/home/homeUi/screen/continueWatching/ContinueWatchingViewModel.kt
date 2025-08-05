package com.feature.home.homeUi.screen.continueWatching

import com.paris_2.domain.media.useCase.GetMediaFromLocalUseCase
import com.feature.home.homeUi.common.BaseViewModel
import com.feature.home.homeUi.mapper.toMediaUiStateList
import com.feature.home.homeUi.screen.home.MediaTypeUi
import com.feature.home.homeUi.screen.home.MediaUiState
import com.feature.mediaDetails.mediaDetailsApi.MediaDetailsFeatureAPI
import com.feature.home.homeUi.navigation.HomeNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ContinueWatchingViewModel @Inject constructor(
    private val getMediaFromLocalUseCase: GetMediaFromLocalUseCase,
    private val mediaDetailsFeatureAPI: MediaDetailsFeatureAPI,
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
                when (media.type) {
                    MediaTypeUi.MOVIE -> mediaDetailsFeatureAPI.startMovieDetails(
                        movieId = media.id
                    )

                    MediaTypeUi.TVSHOW -> mediaDetailsFeatureAPI.startTvShowDetails(
                        tvShowId = media.id
                    )
                }
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
}