package com.feature.home.homeUi.screen.home

import com.feature.home.homeUi.screen.home.components.SliderMedia
import com.paris.domain.media.entity.Category


interface HomeScreenInteractionListener {
    fun onSearchIconClick()
    fun onMediaCardClick(media:MediaUiState,)
    fun onMediaSliderClick(media: SliderMedia)
    fun moodPickerSelected(mood: List<Category>)
    fun onCategorySelect(category: Category)
    fun onAllCategoriesSelect()
    fun onDismissMoodPicker()
    fun getRandomMoodPickerMovie()
    fun onRetry()
}