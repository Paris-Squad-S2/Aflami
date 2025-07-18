package com.feature.mediaDetails.mediaDetailsUi.ui.comon

import com.feature.mediaDetails.mediaDetailsUi.ui.screen.movie.details.MovieUi


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