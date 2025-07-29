package com.feature.mediaDetails.mediaDetailsUi.ui.screen.tvShow.details

interface TvShowScreenInteractionListener {
    fun onRateClick()
    fun onAddToListClick()
    fun onDismissAddToListDialog()
    fun onShowAllCastClick(tvShowId: Int)
    fun onClickOnSeason(seasonNumber: Int)
    fun onSimilarTvShowClick(mediaId: Int)
    fun onRetryLoadTvShowDetails()
    fun onDismissRatingDialog()
    fun onRatingSubmitted(movieId: Int, rating: Float)
    fun onClickPlayEpisodeTrailer(tvShowId: Int, seasonNumber: Int, episodeNumber: Int)
    fun onHideSnackBar()
}