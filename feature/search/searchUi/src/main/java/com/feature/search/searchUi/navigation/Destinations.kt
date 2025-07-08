package com.feature.search.searchUi.navigation

import kotlinx.serialization.Serializable

sealed interface Destinations {
    @Serializable
    object SearchScreen

    @Serializable
    data class FilterScreen(
        val name: String,
    )

    @Serializable
    data class WorldTourScreen(
        val name: String,
    )

    @Serializable
    data class FindByActorScreen(
        val name: String,
    )
}