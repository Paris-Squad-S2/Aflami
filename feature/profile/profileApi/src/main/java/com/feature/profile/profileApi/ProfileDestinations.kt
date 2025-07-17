package com.feature.profile.profileApi

import kotlinx.serialization.Serializable

sealed interface ProfileDestinations : ProfileGraph {

    @Serializable
    data object ProfileGraph1 : ProfileGraph

    @Serializable
    data object ProfileScreen : ProfileDestination
}