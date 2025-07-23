package com.feature.home.homeUi.screen.home

interface HomeScreenInteractionListener {
    fun onSearchIconClick()
    fun onMediaCardClick(media:MediaUiState,)
    fun navigateToContinueWatchingScreen()
    fun navigateToTopRatingScreen()
    fun moodPickerSelected(mood: List<String>)
    fun onCategorySelect(category: List<Int>)
    fun onAllCategoriesSelect()
}