package com.feature.search.searchUi.navigation

import com.paris_2.aflami.appnavigation.Destination
import com.paris_2.aflami.appnavigation.Graph
import kotlinx.serialization.Serializable

sealed interface SearchDestinations : Graph {

    @Serializable
    data object SearchGraph : Graph

    @Serializable
    data object SearchScreen : Destination

    @Serializable
    data class WorldTourScreen(val name: String? = null) : Destination

    @Serializable
    data class FindByActorScreen(val name: String? = null) : Destination
}