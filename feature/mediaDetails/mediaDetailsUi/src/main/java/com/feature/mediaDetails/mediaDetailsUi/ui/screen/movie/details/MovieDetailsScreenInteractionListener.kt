package com.feature.mediaDetails.mediaDetailsUi.ui.screen.movie.details

interface MovieDetailsScreenInteractionListener {
    fun onNavigateBack()
    fun onFavouriteClick(title:Int)
    fun onAddToListClick(title:Int)
    fun onShowAllCastClick(movieId: Int)
    fun onRetryLoadMovieDetails()
}