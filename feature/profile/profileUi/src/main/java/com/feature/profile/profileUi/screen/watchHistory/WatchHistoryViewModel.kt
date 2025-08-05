package com.feature.profile.profileUi.screen.watchHistory

import com.feature.mediaDetails.mediaDetailsApi.MediaDetailsFeatureAPI
import com.feature.profile.profileUi.common.BaseViewModel
import com.feature.profile.profileUi.mapper.toMediaUiStateList
import com.feature.profile.profileUi.mapper.toMediaType
import com.feature.profile.profileUi.navigation.ProfileNavigator
import com.paris_2.domain.media.useCase.FilterWatchHistoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class WatchHistoryViewModel @Inject constructor(
    private val filterWatchHistoryUseCase: FilterWatchHistoryUseCase,
    private val mediaDetailsFeatureAPI: MediaDetailsFeatureAPI,
    navigator: ProfileNavigator,
) : WatchHistoryInteractionListener, BaseViewModel<WatchHistoryUiState>(
    navigator = navigator,
    initialState = WatchHistoryUiState(
        isLoading = false,
        errorMessage = null,
        watchHistoryMedia = emptyList()
    )
){
    private var selectedMediaType: MediaTypeUi = MediaTypeUi.MOVIE
    init {
        loadWatchHistoryMedia(selectedMediaType)
    }
    private fun loadWatchHistoryMedia(mediaType: MediaTypeUi) {
        tryToExecute(
            execute = {
                updateState(
                    screenState.value.copy(
                        isLoading = true
                    )
                )
                filterWatchHistoryUseCase.invoke(mediaType.toMediaType())
            },
            onSuccess = { mediaList ->
                updateState(
                    screenState.value.copy(
                        watchHistoryMedia = mediaList.toMediaUiStateList(),
                        isLoading = false
                    )
                )},
            onError = {error ->
                updateState(
                    screenState.value.copy(
                        errorMessage = error,
                        isLoading = false
                    )
                )
            }
        )
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
            updateState(
                screenState.value.copy(
                    errorMessage = errorMessage
                )
            )
        })
    }

    override fun onBackClick() {
        tryToExecute(
            execute = {
                navigateUp()
            },
            onError = { errorMessage ->
                updateState(
                    screenState.value.copy(
                        errorMessage = errorMessage,
                    )
                )
            }
        )
    }

    fun onTabSelected(mediaTypeUi: MediaTypeUi) {
        selectedMediaType = mediaTypeUi
        loadWatchHistoryMedia(mediaTypeUi)
    }

    fun onRetry(){
        loadWatchHistoryMedia(selectedMediaType)
    }
}