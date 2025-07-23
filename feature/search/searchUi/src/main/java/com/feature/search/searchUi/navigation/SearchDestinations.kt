package com.feature.search.searchUi.navigation

import kotlinx.serialization.Serializable

sealed interface SearchDestinations : SearchGraph {

    @Serializable
    data object SearchGraph1 : SearchGraph

    @Serializable
    data object SearchScreen : SearchDestination

    @Serializable
    data class WorldTourScreen(val name: String? = null) : SearchDestination

    @Serializable
    data class FindByActorScreen(val name: String? = null) : SearchDestination
}