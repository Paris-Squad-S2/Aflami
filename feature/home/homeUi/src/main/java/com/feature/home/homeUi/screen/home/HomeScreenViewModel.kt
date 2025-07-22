package com.feature.home.homeUi.screen.home

import com.domain.home.usecase.GetPopularMediaUseCase
import com.domain.home.usecase.GetTopRatingMediaUseCase
import com.feature.home.homeUi.common.BaseViewModel
import com.feature.home.homeUi.fake.FakeContinueWatchingUseCase
import com.feature.home.homeUi.mapper.toMediaUiStateList

class HomeScreenViewModel(
    private val getPopularMediaUseCase: GetPopularMediaUseCase,
    private val getTopRatingMediaUseCase: GetTopRatingMediaUseCase,
    private val fakeContinueWatchingUseCase: FakeContinueWatchingUseCase
) : HomeScreenInteractionListener,
    BaseViewModel<HomeScreenUIState>(
        HomeScreenUIState(
            homeUIState = HomeUIState(
                popularMediaList = emptyList(),
                continueWatchingMediaList = emptyList(),
                topRatedMediaList = emptyList(),
                moviesBirthdayMediaList = emptyList(),
                categories = emptyMap(),
                upComingMediaList = emptyList(),
                showMoodPickerDialog = false
            ),
            isLoading = false,
            errorMessage = null
        )
    ) {
     init {
         loadPopularMedia()
         loadTopRatingMedia()
         loadContinueWatchingMedia()
     }

    private fun loadPopularMedia (){
        tryToExecute(
            execute = getPopularMediaUseCase::invoke,
            onSuccess = {
                emitState(
                    screenState.value.copy(
                        homeUIState = screenState.value.homeUIState.copy(
                            popularMediaList = it.toMediaUiStateList()
                        )
                    )
                )
            },
            onError = {
                // Todo(handle error)
            }
        )
    }

    private fun loadTopRatingMedia() {
        tryToExecute(
            execute = fakeContinueWatchingUseCase::invoke
            ,
            onSuccess ={ topRatingMedia ->
                emitState(
                    screenState.value.copy(
                        homeUIState = screenState.value.homeUIState.copy(
                            topRatedMediaList = topRatingMedia
                        )
                    )
                )
            } ,
            onError = {errorMessage->
                emitState(
                    screenState.value.copy(
                        errorMessage =  errorMessage
                    )
                )
            },
        )
    }

    private fun loadContinueWatchingMedia(){
        tryToExecute(
            execute = fakeContinueWatchingUseCase::invoke,
            onSuccess = { mediaList ->
                emitState(
                    screenState.value.copy(
                        homeUIState = screenState.value.homeUIState.copy(
                            continueWatchingMediaList = mediaList
                        )
                    )
                )
            },
            onError = {}
        )
    }

    override fun onSearchIconClick() {
        TODO("Not yet implemented")
    }

    override fun onMediaCardClick(mediaId: Int) {
        TODO("Not yet implemented")
    }

    override fun navigateToContinueWatchingScreen() {
        TODO("Not yet implemented")
    }

    override fun navigateToTopRatingScreen() {
        TODO("Not yet implemented")
    }

    override fun navigateToMoviesBirthdayScreen() {
        TODO("Not yet implemented")
    }

    override fun moodPickerSelected(mood: String) {
        TODO("Not yet implemented")
    }

    override fun onCategorySelect(category: Int) {
        TODO("Not yet implemented")
    }
}

// Todo( add to continue watching list use case) need to local data
// Todo( get continue Watching List use case ) from local data source