package com.feature.mediaDetails.mediaDetailsUi.ui.screen.movie.details

interface MovieDetailsScreenInteractionListener {
    fun onRateClick()
    fun onAddToListClick()
    fun onShowAllCastClick(movieId: Int)
    fun onRetryLoadMovieDetails()
    fun onSimilarMovieClick(mediaId: Int)
    fun onDismissRatingDialog()
    fun onRatingSubmitted(movieId: Int, rating: Float)
    fun onHideSnackBar()

    fun onListSelectionChanged(index: Int)
    fun onAddToSelectedList()
    fun onDismissAddToListDialog()

    fun onCreateListShow()
    fun onCreateListDismiss()
    fun onCreateListNameChange(name: String)
    fun onCreateListConfirm()

    fun playYoutubeVideo(videoKey: String)
    fun closeYoutubePlayer()
}