package com.feature.profile.profileUi.screen.watchHistory

import com.feature.mediaDetails.mediaDetailsApi.MediaDetailsFeatureAPI
import com.feature.profile.profileUi.common.BaseViewModel
import com.feature.profile.profileUi.mapper.toMediaType
import com.feature.profile.profileUi.mapper.toMediaUiStateList
import com.paris.domain.media.useCase.media.FilterWatchHistoryUseCase
import com.paris.domain.user.usecase.SettingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class WatchHistoryViewModel @Inject constructor(
    private val filterWatchHistoryUseCase: FilterWatchHistoryUseCase,
    private val mediaDetailsFeatureAPI: MediaDetailsFeatureAPI,
    private val settingsUseCase: SettingsUseCase,
) : WatchHistoryInteractionListener, BaseViewModel<WatchHistoryUiState>(WatchHistoryUiState()) {
    private var selectedMediaType: MediaTypeUi = MediaTypeUi.MOVIE

    init {
        getRestriction()
        loadWatchHistoryMedia(selectedMediaType)
    }

    private fun getRestriction() {
        tryToExecute(
            execute = { settingsUseCase.getRestriction() },
            onSuccess = ::onGetRestrictionSuccess,
            onError = ::onGetRestrictionError
        )
    }

    private fun onGetRestrictionSuccess(restriction: String) {
        updateState(
            screenState.value.copy(
                contentRestriction = ContentRestriction.valueOf(restriction),
            )
        )
        when (screenState.value.contentRestriction) {
            ContentRestriction.Strict -> updateState(
                screenState.value.copy(
                    nsfwThreshold = 0.8f,
                    genderThreshold = 0.6f
                )
            )

            ContentRestriction.Moderate -> updateState(
                screenState.value.copy(
                    nsfwThreshold = 0.4f,
                    genderThreshold = 0.6f
                )
            )

            ContentRestriction.Off -> updateState(
                screenState.value.copy(
                    nsfwThreshold = 0f,
                    genderThreshold = 0f
                )
            )
        }

    }

    private fun onGetRestrictionError(error: String) {
        updateState(
            screenState.value.copy(
                errorMessage = error,
            )
        )
    }

    private fun loadWatchHistoryMedia(mediaType: MediaTypeUi) {
        tryToExecute(
            execute = {
                updateState(
                    screenState.value.copy(
                        isLoading = true
                    )
                )
                filterWatchHistoryUseCase(mediaType.toMediaType())
            },
            onSuccess = { mediaListFlow ->
                mediaListFlow.collect { mediaList ->
                    updateState(
                        screenState.value.copy(
                            watchHistoryMedia = mediaList.toMediaUiStateList(),
                            isLoading = false
                        )
                    )
                }
            },
            onError = { error ->
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

    fun onTabSelected(mediaTypeUi: MediaTypeUi) {
        if (mediaTypeUi == selectedMediaType) return
        selectedMediaType = mediaTypeUi
        loadWatchHistoryMedia(mediaTypeUi)
    }

    fun onRetry() {
        loadWatchHistoryMedia(selectedMediaType)
    }
}