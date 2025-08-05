package com.feature.profile.profileUi.navigation

import kotlinx.serialization.Serializable

sealed interface ProfileDestinations : ProfileGraph {

    @Serializable
    data object ProfileGraph1 : ProfileGraph

    @Serializable
    data object WatchHistoryScreen : ProfileDestination

}