package com.feature.mediaDetails.mediaDetailsUi.ui.screen.movie.details

interface MovieDetailsScreenInteractionListener {
    fun onNavigateBack()
    fun onFavouriteClick(title:String)
    fun onAddToListClick(title:String)
    fun onShowAllCastClick(movieId: Int)
}