package com.feature.home.homeUi.screen.topRatingMovies

import com.feature.home.homeUi.screen.home.MediaUiState

interface TopRatingInteractionListener {
    fun onMediaCardClick(media: MediaUiState)
    fun onBackButtonClick()
}