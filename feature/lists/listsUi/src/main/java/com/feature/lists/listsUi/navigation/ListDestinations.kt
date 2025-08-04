package com.feature.lists.listsUi.navigation

import kotlinx.serialization.Serializable

sealed interface ListDestinations : ListGraph {

    @Serializable
    data object ListGraph1 : ListGraph

    @Serializable
    data object ListScreen : ListDestination

    @Serializable
    data object ListDetails : ListDestination

}