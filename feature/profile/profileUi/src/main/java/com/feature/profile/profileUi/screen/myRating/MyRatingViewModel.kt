package com.feature.profile.profileUi.screen.myRating

import com.feature.mediaDetails.mediaDetailsApi.MediaDetailsFeatureAPI
import com.feature.profile.profileUi.common.BaseViewModel
import com.feature.profile.profileUi.mapper.toMediaType
import com.feature.profile.profileUi.mapper.toMediaUiStateList
import com.feature.profile.profileUi.navigation.ProfileNavigator
import com.feature.profile.profileUi.screen.watchHistory.MediaTypeUi
import com.feature.profile.profileUi.screen.watchHistory.MediaUiState
import com.paris_2.domain.media.useCase.FilterRatedMediaUseCase
import javax.inject.Inject
import dagger.hilt.android.lifecycle.HiltViewModel


@HiltViewModel
class MyRatingViewModel @Inject constructor(
    private val getRatedMediaUseCase: FilterRatedMediaUseCase,
    private val mediaDetailsFeatureAPI: MediaDetailsFeatureAPI,
    navigator: ProfileNavigator,
) : MyRatingInteractionListener, BaseViewModel<MyRatingUiState>(
    navigator = navigator,
    initialState = MyRatingUiState(
        isLoading = false,
        errorMessage = null,
        myRatingMedia = emptyList()
    )
) {
    private var selectedMediaType: MediaTypeUi = MediaTypeUi.MOVIE
    init {
        loadMyRatingMedia(selectedMediaType)
    }

    private fun loadMyRatingMedia(mediaType: MediaTypeUi) {
        tryToExecute(
            execute = {
                updateState(
                    screenState.value.copy(
                        isLoading = true
                    )
                )
                getRatedMediaUseCase.invoke(mediaType.toMediaType())
            },
            onSuccess = { mediaList ->
                updateState(
                    screenState.value.copy(
                        myRatingMedia = mediaList.toMediaUiStateList(),
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
        loadMyRatingMedia(mediaTypeUi)
    }

    fun onRetry(){
        loadMyRatingMedia(selectedMediaType)
    }
}