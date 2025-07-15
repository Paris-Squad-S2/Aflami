package com.paris_2.aflami.appnavigation

import kotlinx.serialization.Serializable

sealed interface AppDestinations : Graph {

    @Serializable
    data object AppGraph : Graph

    @Serializable
    data object SearchFeature : Destination

    @Serializable
    data object DetailsFeature : Destination
}