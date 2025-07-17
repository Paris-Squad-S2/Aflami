package com.feature.mediaDetails.mediaDetailsUi.ui.screen.tvShow.details

interface TvShowScreenInteractionListener {
    fun onNavigateBack()
    fun onFavouriteClick()
    fun onAddToListClick()
    fun onShowAllCastClick(tvShowId: Int)
}