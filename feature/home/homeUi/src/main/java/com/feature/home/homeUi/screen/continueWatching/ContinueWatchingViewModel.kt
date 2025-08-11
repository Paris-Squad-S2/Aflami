package com.feature.home.homeUi.screen.continueWatching

import com.feature.home.homeUi.common.BaseViewModel
import com.feature.home.homeUi.common.ContentRestriction
import com.feature.home.homeUi.mapper.toMediaUiStateList
import com.feature.home.homeUi.screen.home.MediaTypeUi
import com.feature.home.homeUi.screen.home.MediaUiState
import com.feature.mediaDetails.mediaDetailsApi.MediaDetailsFeatureAPI
import com.paris_2.domain.media.useCase.GetWatchHistoryUseCase
import com.paris_2.domain.user.usecase.SettingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ContinueWatchingViewModel @Inject constructor(
    private val getWatchHistoryUseCase: GetWatchHistoryUseCase,
    private val mediaDetailsFeatureAPI: MediaDetailsFeatureAPI,
    private val settingsUseCase: SettingsUseCase,
) : BaseViewModel<ContinueWatchingUiState>(
    ContinueWatchingUiState(
        continueWatchingMediaList = emptyList(),
        isLoading = false,
        errorMessage = null
    )
), ContinueWatchingInteractionListener {

    init {
        getRestriction()
        loadContinueWatchingMedia()
    }

    private fun getRestriction() {
        tryToExecute(
            execute = { settingsUseCase.getRestriction() },
            onSuccess = ::onGetRestrictionSuccess,
            onError = ::onGetRestrictionError
        )
    }

    private fun onGetRestrictionSuccess(restriction: String) {
        emitState(
            screenState.value.copy(
                contentRestriction = ContentRestriction.valueOf(restriction),
            )
        )
        when (screenState.value.contentRestriction) {
            ContentRestriction.STRICT -> emitState(
                screenState.value.copy(
                    nsfwThreshold = 0.8f,
                    genderThreshold = 0.6f
                )
            )

            ContentRestriction.MODERATE -> emitState(
                screenState.value.copy(
                    nsfwThreshold = 0.4f,
                    genderThreshold = 0.6f
                )
            )

            ContentRestriction.OFF -> emitState(
                screenState.value.copy(
                    nsfwThreshold = 0f,
                    genderThreshold = 0f
                )
            )
        }

    }

    private fun onGetRestrictionError(error: String) {
        emitState(
            screenState.value.copy(
                errorMessage = error,
            )
        )
    }

    private fun loadContinueWatchingMedia() {
        tryToExecute(
            execute = {
                emitState(
                    screenState.value.copy(
                        isLoading = true
                    )
                )
                getWatchHistoryUseCase.invoke()
            },
            onSuccess = { mediaList ->
                emitState(
                    screenState.value.copy(
                        continueWatchingMediaList = mediaList.toMediaUiStateList(),
                        isLoading = false
                    )
                )
            },
            onError = { error ->
                emitState(
                    screenState.value.copy(
                        errorMessage = error,
                        isLoading = false
                    )
                )
            }
        )
    }

    fun onRetry() {
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