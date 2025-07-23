package com.feature.home.homeUi.screen.topRatingMovies

import com.domain.home.usecase.GetTopRatingMediaUseCase
import com.feature.home.homeUi.common.BaseViewModel
import com.feature.home.homeUi.mapper.toMediaUiStateList
import com.feature.home.homeUi.screen.home.MediaTypeUi
import com.feature.home.homeUi.screen.home.MediaUiState
import com.feature.mediaDetails.mediaDetailsApi.MediaDetailsDestinations
import com.feature.mediaDetails.mediaDetailsApi.toJson
import com.paris_2.aflami.appnavigation.AppDestinations
import com.paris_2.aflami.appnavigation.AppNavigator

class TopRatingMoviesViewModel(
    private val getTopRatingMediaUseCase: GetTopRatingMediaUseCase,
    private val appNavigator: AppNavigator
):BaseViewModel<TopRatingMoviesUiState>(
    TopRatingMoviesUiState(
        topRatingMovies = emptyList(),
        isLoading = false,
        errorMessage = null
    )
) ,TopRatingInteractionListener {

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
}