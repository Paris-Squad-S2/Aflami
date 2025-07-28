package com.feature.mediaDetails.mediaDetailsUi.ui.screen.tvShow.details

interface TvShowScreenInteractionListener {
    fun onRateClick(title: Int)
    fun onAddToListClick(title: Int)
    fun onShowAllCastClick(tvShowId: Int)
    fun onClickOnSeason(seasonNumber: Int)
    fun onSimilarTvShowClick(mediaId: Int)
    fun onRetryLoadTvShowDetails()
    fun onDismissRatingDialog()
    fun onRatingSubmitted(movieId: Int, rating: Float)
    fun onClickPlayEpisodeTrailer(tvShowId: Int, seasonNumber: Int, episodeNumber: Int)
    fun onHideSnackBar()
}