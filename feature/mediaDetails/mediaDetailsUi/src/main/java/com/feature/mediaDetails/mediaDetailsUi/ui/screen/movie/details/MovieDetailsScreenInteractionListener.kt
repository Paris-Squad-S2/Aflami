package com.feature.mediaDetails.mediaDetailsUi.ui.screen.movie.details

interface MovieDetailsScreenInteractionListener {
    fun onRateClick()
    fun onAddToListClick(title:Int)
    fun onDismissAddToListDialog()
    fun onShowAllCastClick(movieId: Int)
    fun onRetryLoadMovieDetails()
    fun onSimilarMovieClick(mediaId: Int)
    fun onDismissRatingDialog()
    fun onRatingSubmitted(movieId: Int, rating: Float)
    fun onHideSnackBar()
}