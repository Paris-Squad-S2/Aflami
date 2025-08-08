package com.feature.profile.profileUi.screen.myRating

import com.feature.profile.profileUi.screen.watchHistory.MediaUiState

interface MyRatingInteractionListener {
    fun onMediaCardClick(media: MediaUiState)
    fun onBackClick()
    fun onFavouriteIconClick(media: MediaUiState)
}