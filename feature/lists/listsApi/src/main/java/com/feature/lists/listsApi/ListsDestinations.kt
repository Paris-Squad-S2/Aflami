package com.feature.lists.listsApi

import kotlinx.serialization.Serializable

sealed interface ListsDestinations : ListsGraph {

    @Serializable
    data object ListsGraph1 : ListsGraph

    @Serializable
    data object ListsScreen : ListsDestination
}