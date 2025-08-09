package com.paris_2.aflami.bottomNavBar.bottomNavBarUI.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface Destinations : Graph {

    @Serializable
    data object MainGraph : Graph

    @Serializable
    data object HomeFeature : Destination

    @Serializable
    data object ListsFeature : Destination

    @Serializable
    data object CategoriesFeature : Destination

    @Serializable
    data object LetsPlayFeature : Destination

    @Serializable
    data object ProfileFeature : Destination
}