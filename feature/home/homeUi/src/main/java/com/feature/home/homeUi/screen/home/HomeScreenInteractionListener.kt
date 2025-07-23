package com.feature.home.homeUi.screen.home

import com.paris_2.aflami.designsystem.components.SliderMedia

interface HomeScreenInteractionListener {
    fun onSearchIconClick()
    fun onMediaCardClick(media:MediaUiState,)
    fun onMediaSliderClick(media: SliderMedia)
    fun navigateToContinueWatchingScreen()
    fun navigateToTopRatingScreen()
    fun moodPickerSelected(mood: List<String>)
    fun onCategorySelect(category: CategoryUiState)
    fun onAllCategoriesSelect()
    fun onDismissMoodPicker()
    fun getRandomMoodPickerMovie()
}