package com.feature.home.homeUi.screen.topRatingMovies

import com.domain.home.usecase.GetTopRatingMediaUseCase
import com.feature.home.homeUi.common.BaseViewModel
import com.feature.home.homeUi.mapper.toMediaUiStateList
import com.feature.home.homeUi.navigation.HomeNavigator
import com.feature.home.homeUi.screen.home.MediaTypeUi
import com.feature.home.homeUi.screen.home.MediaUiState
import com.feature.mediaDetails.mediaDetailsApi.MediaDetailsFeatureAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TopRatingMoviesViewModel @Inject constructor(
    private val getTopRatingMediaUseCase: GetTopRatingMediaUseCase,
    private val mediaDetailsFeatureAPI: MediaDetailsFeatureAPI,
    navigator: HomeNavigator,
) : BaseViewModel<TopRatingMoviesUiState>(
    TopRatingMoviesUiState(
        topRatingMovies = emptyList(), isLoading = false, errorMessage = null
    ), navigator
), TopRatingInteractionListener {

    init {
        loadContinueWatchingMedia()
    }

    private fun loadContinueWatchingMedia() {
        tryToExecute(execute = {
            emitState(
                screenState.value.copy(
                    isLoading = true
                )
            )
            getTopRatingMediaUseCase.invoke()
        }, onSuccess = { mediaList ->
            emitState(
                screenState.value.copy(
                    topRatingMovies = mediaList.toMediaUiStateList(), isLoading = false
                )
            )
        }, onError = { error ->
            emitState(
                screenState.value.copy(
                    errorMessage = error, isLoading = false
                )
            )
        })
    }

    fun onRetry() {
        loadContinueWatchingMedia()
    }

    override fun onMediaCardClick(media: MediaUiState) {
        tryToExecute(execute = {
            when (media.type) {
                MediaTypeUi.MOVIE -> mediaDetailsFeatureAPI.startMovieDetails(
                    movieId = media.id
                )

                MediaTypeUi.TVSHOW -> mediaDetailsFeatureAPI.startTvShowDetails(
                    tvShowId = media.id
                )
            }
        }, onError = { errorMessage ->
            emitState(
                screenState.value.copy(
                    errorMessage = errorMessage
                )
            )
        })
    }
}