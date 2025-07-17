package com.feature.mediaDetails.mediaDetailsUi.ui.screen.movie.details

interface MovieDetailsScreenInteractionListener {
    fun onNavigateBack()
    fun onFavouriteClick()
    fun onAddToListClick()
    fun onShowAllCastClick(movieId: Int)
}