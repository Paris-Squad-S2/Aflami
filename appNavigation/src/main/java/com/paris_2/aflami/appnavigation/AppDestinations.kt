package com.paris_2.aflami.appnavigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface AppDestinations : AppGraph {

    @Serializable
    data object AppGraph1 : AppGraph

    @Serializable
    data class SearchFeature(val searchDestination: String? = null) : AppDestination

    @Serializable
    data object DetailsFeature : AppDestination
}