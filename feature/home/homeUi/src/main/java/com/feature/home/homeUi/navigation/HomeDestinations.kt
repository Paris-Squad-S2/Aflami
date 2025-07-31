package com.feature.home.homeUi.navigation

import kotlinx.serialization.Serializable

sealed interface HomeDestinations : HomeGraph {

    @Serializable
    data object HomeGraph1 : HomeGraph

    @Serializable
    data object HomeScreen : HomeDestination

    @Serializable
    data object ContinueWatchingScreen : HomeDestination

    @Serializable
    data object TopRatingMoviesScreen : HomeDestination
}