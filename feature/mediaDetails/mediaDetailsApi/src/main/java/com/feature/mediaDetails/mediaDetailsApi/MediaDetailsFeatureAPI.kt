package com.feature.mediaDetails.mediaDetailsApi

interface MediaDetailsFeatureAPI {
    fun startMovieDetails(movieId: Int)
    fun startTvShowDetails(tvShowId: Int)
}