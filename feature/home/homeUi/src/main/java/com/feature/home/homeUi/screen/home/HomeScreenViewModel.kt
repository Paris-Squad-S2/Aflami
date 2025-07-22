package com.feature.home.homeUi.screen.home

import com.feature.home.homeUi.common.BaseViewModel

class HomeScreenViewModel() : HomeScreenInteractionListener,
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