package com.feature.home.homeApi

import kotlinx.serialization.Serializable

sealed interface HomeDestinations : HomeGraph {

    @Serializable
    data object HomeGraph1 : HomeGraph

    @Serializable
    data class MovieDetailsScreen(val movieId: Int) : HomeDestination

    @Serializable
    data class TvShowDetailsScreen(val tvShowId: Int) : HomeDestination
}