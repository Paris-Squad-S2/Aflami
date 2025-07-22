package com.feature.home.homeUi.screen.home

interface HomeScreenInteractionListener {
    fun onSearchIconClick()
    fun onMediaCardClick(mediaId: Int)
    fun navigateToContinueWatchingScreen()
    fun navigateToTopRatingScreen()
    fun navigateToMoviesBirthdayScreen()
    fun moodPickerSelected(mood: String)
    fun onCategorySelect(category: Int)
}