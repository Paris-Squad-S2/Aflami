package com.feature.mediaDetails.mediaDetailsUi.ui.screen.tvShow.details

interface TvShowScreenInteractionListener {
    fun onNavigateBack()
    fun onFavouriteClick(title:Int)
    fun onAddToListClick(title: Int)
    fun onShowAllCastClick(tvShowId: Int)
    fun onClickOnSeason(seasonNumber: Int)
}