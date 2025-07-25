package com.feature.home.homeApi

import kotlinx.serialization.Serializable

sealed interface HomeDestinations : HomeGraph {

    @Serializable
    data object HomeGraph1 : HomeGraph

    @Serializable
    data object HomeScreen : HomeDestination

    @Serializable
    data object ContinueWatchingScreen : HomeDestination

    @Serializable
    data object MoviesBirthdayScreen : HomeDestination

    @Serializable
    data object TopRatingMoviesScreen : HomeDestination
}