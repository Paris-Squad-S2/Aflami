package com.feature.mediaDetails.mediaDetailsUi.ui.comon

import android.content.Context
import android.content.Intent
import androidx.core.net.toUri
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.movie.details.MovieUi
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.tvShow.details.TvShowUi


fun hasDescriptionContent(movie: MovieUi): Boolean {
    return when {
        movie.description.isNotBlank() -> true
        movie.genres.isNotEmpty() -> true
        movie.releaseDate.isNotBlank() -> true
        movie.runtime > 0.toString() -> true
        movie.country.isNotBlank() -> true
        else -> false
    }
}

fun hasDescriptionContent(movie: TvShowUi): Boolean {
    return when {
        movie.description.isNotBlank() -> true
        movie.genres.isNotEmpty() -> true
        movie.releaseDate.isNotBlank() -> true
        movie.runtime > 0.toString() -> true
        movie.country.isNotBlank() -> true
        else -> false
    }
}

fun Context.openYoutubeOrBrowser(videoKey: String) {
    val youtubeIntent = Intent(Intent.ACTION_VIEW, ("vnd.youtube:$videoKey").toUri())
    youtubeIntent.setPackage("com.google.android.youtube")
    val browserIntent = Intent(Intent.ACTION_VIEW, ("https://www.youtube.com/watch?v=$videoKey").toUri())
    try {
        startActivity(youtubeIntent)
    } catch (_: Exception) {
        startActivity(browserIntent)
    }
}
