package com.feature.search.searchUi.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.feature.search.searchUi.screen.findByActor.FindByActorScreen
import com.feature.search.searchUi.screen.search.SearchScreen
import com.feature.search.searchUi.screen.worldTour.WorldTourScreen
import kotlinx.serialization.Serializable

sealed interface Destinations : Graph {

    @Serializable
    data object SearchGraph : Graph

    @Serializable
    data object SearchScreen : Destination

    @Serializable
    data class WorldTourScreen(val name: String) : Destination

    @Serializable
    data class FindByActorScreen(val name: String) : Destination
}

fun NavGraphBuilder.buildSearchNavGraph() {
    navigation<Destinations.SearchGraph>(startDestination = Destinations.SearchScreen) {
        composable<Destinations.SearchScreen> { SearchScreen() }
        composable<Destinations.WorldTourScreen> { WorldTourScreen() }
        composable<Destinations.FindByActorScreen> { FindByActorScreen() }
    }
}
