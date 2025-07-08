package com.feature.search.searchUi.navigation

import kotlinx.serialization.Serializable

sealed interface Destinations : Graph {
    @Serializable
    data object SearchGraph : Graph

    @Serializable
    data object SearchScreen : Destination

    @Serializable
    data class FilterScreen(
        val name: String,
    ) : Destination

    @Serializable
    data class WorldTourScreen(
        val name: String,
    ) : Destination

    @Serializable
    data class FindByActorScreen(
        val name: String,
    ) : Destination
}