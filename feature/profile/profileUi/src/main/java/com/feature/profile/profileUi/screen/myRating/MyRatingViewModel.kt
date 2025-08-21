package com.feature.profile.profileUi.screen.myRating

import com.feature.mediaDetails.mediaDetailsApi.MediaDetailsFeatureAPI
import com.feature.profile.profileUi.common.BaseViewModel
import com.feature.profile.profileUi.mapper.toMediaType
import com.feature.profile.profileUi.mapper.toMediaUiStateList
import com.feature.profile.profileUi.screen.watchHistory.MediaTypeUi
import com.feature.profile.profileUi.screen.watchHistory.MediaUiState
import com.paris.domain.media.useCase.media.FilterRatedMediaUseCase
import com.paris.domain.media.useCase.movie.DeleteMovieRatingUseCase
import com.paris.domain.media.useCase.tvShows.DeleteTvShowRatingUseCase
import com.paris.domain.user.usecase.auth.GetAccountIdUseCase
import com.paris.domain.user.usecase.ManageSettingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyRatingViewModel @Inject constructor(
    private val getRatedMediaUseCase: FilterRatedMediaUseCase,
    private val mediaDetailsFeatureAPI: MediaDetailsFeatureAPI,
    private val getAccountIdUseCase: GetAccountIdUseCase,
    private val deleteMovieRatingUseCase: DeleteMovieRatingUseCase,
    private val deleteTvShowRatingUseCase: DeleteTvShowRatingUseCase,
    private val manageSettingsUseCase: ManageSettingsUseCase,
) : MyRatingInteractionListener, BaseViewModel<MyRatingUiState>(MyRatingUiState()) {
    private var selectedMediaType: MediaTypeUi = MediaTypeUi.MOVIE

    init {
        getRestriction()
        loadMyRatingMedia(selectedMediaType)
    }

    private fun getRestriction() {
        tryToExecute(
            execute = { manageSettingsUseCase.getRestriction() },
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

    private fun loadMyRatingMedia(mediaType: MediaTypeUi) {
        tryToExecute(
            execute = {
                updateState(
                    screenState.value.copy(
                        isLoading = true
                    )
                )
                getRatedMediaUseCase(
                    accountId = getAccountIdUseCase() ?: 0,
                    mediaType = mediaType.toMediaType()
                )
            },
            onSuccess = { mediaList ->
                updateState(
                    screenState.value.copy(
                        myRatingMedia = mediaList.toMediaUiStateList(),
                        isLoading = false
                    )
                )
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

    override fun onFavouriteIconClick(media: MediaUiState) {
        tryToExecute(
            execute = {
                updateState(
                    screenState.value.copy(
                        isLoading = true
                    )
                )
                when (media.type) {
                    MediaTypeUi.MOVIE -> deleteMovieRatingUseCase(media.id)
                    MediaTypeUi.TVSHOW -> deleteTvShowRatingUseCase(media.id)
                }
                loadMyRatingMedia(selectedMediaType)
            },
            onError = { errorMessage ->
                updateState(
                    screenState.value.copy(
                        errorMessage = errorMessage,
                        isLoading = false
                    )
                )
            }
        )
    }

    fun onTabSelected(mediaTypeUi: MediaTypeUi) {
        if (mediaTypeUi == selectedMediaType) return
        selectedMediaType = mediaTypeUi
        updateState(
            screenState.value.copy(
                isLoading = true
            )
        )
        loadMyRatingMedia(mediaTypeUi)
    }

    fun onRetry() {
        loadMyRatingMedia(selectedMediaType)
    }
}