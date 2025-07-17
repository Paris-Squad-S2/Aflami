package com.feature.mediaDetails.mediaDetailsApi

import kotlinx.serialization.Serializable

sealed interface MediaDetailsDestinations : MediaDetailsGraph {

    @Serializable
    data object MediaDetailsGraph1 : MediaDetailsGraph

    @Serializable
    data class MovieDetailsScreen(val movieId: Int) : MediaDetailsDestination

    @Serializable
    data class TvShowDetailsScreen(val tvShowId: Int) : MediaDetailsDestination

    @Serializable
    data class MovieCastScreen(val movieId: Int) : MediaDetailsDestination

    @Serializable
    data class TvShowCastScreen(val tvShowId: Int) : MediaDetailsDestination

}