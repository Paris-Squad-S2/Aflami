package com.feature.profile.profileUi.navigation

import kotlinx.serialization.Serializable

sealed interface ProfileDestinations : ProfileGraph {

    @Serializable
    data object ProfileMainGraph : ProfileGraph

    @Serializable
    data object ProfileScreen : ProfileDestination

    @Serializable
    data object WebView : ProfileDestination
}