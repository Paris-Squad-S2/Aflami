package com.paris_2.aflami.bottomNavBar

import kotlinx.serialization.Serializable

@Serializable
sealed interface AppDestinations : AppGraph {

    @Serializable
    data object AppGraph1 : AppGraph

    @Serializable
    data object OnBoardingFeature : AppDestination

    @Serializable
    data object HomeFeature : AppDestination

    @Serializable
    data object ListsFeature : AppDestination

    @Serializable
    data object CategoriesFeature : AppDestination

    @Serializable
    data object LetsPlayFeature : AppDestination

    @Serializable
    data object ProfileFeature : AppDestination
}