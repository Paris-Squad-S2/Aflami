package com.feature.mediaDetails.mediaDetailsUi.ui

import android.content.Intent
import com.feature.mediaDetails.mediaDetailsUi.ui.navigation.MediaDetailsDestinations
import com.feature.mediaDetails.mediaDetailsApi.MediaDetailsFeatureAPI
import com.feature.mediaDetails.mediaDetailsUi.ui.navigation.toJson

class MediaDetailsFeatureAPIImpl(
    private val context: android.content.Context
) : MediaDetailsFeatureAPI {

    override fun startMovieDetails(movieId: Int) {
        val intent = Intent(context, MediaDetailsActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.putExtra("mediaDetailsDestination", MediaDetailsDestinations.MovieDetailsScreen(movieId).toJson())
        context.startActivity(intent)
    }

    override fun startTvShowDetails(tvShowId: Int) {
        val intent = Intent(context, MediaDetailsActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.putExtra("mediaDetailsDestination", MediaDetailsDestinations.TvShowDetailsScreen(tvShowId).toJson())
        context.startActivity(intent)
    }
}