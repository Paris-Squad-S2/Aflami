package com.feature.search.searchUi.navigation

sealed class Screen(val route: String) {
    object SearchScreen: Screen("SearchScreen")
    object WorldTourScreen: Screen("WorldTourScreen")
    object FindByActorScreen: Screen("FindByActorScreen")
}