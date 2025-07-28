package com.feature.mediaDetails.mediaDetailsUi.ui.screen.movie.details

interface MovieDetailsScreenInteractionListener {
    fun onFavouriteClick(title:Int)
    fun onAddToListClick(title:Int)
    fun onShowAllCastClick(movieId: Int)
    fun onRetryLoadMovieDetails()
    fun onSimilarMovieClick(mediaId: Int)
    fun onDismissRatingDialog()
    fun onRatingSubmitted(movieId: Int, rating: Float)
}