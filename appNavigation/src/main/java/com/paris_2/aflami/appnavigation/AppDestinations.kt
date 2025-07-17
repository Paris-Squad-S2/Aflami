package com.paris_2.aflami.appnavigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface AppDestinations : AppGraph {

    @Serializable
    data object AppGraph1 : AppGraph

    @Serializable
    data class HomeFeature(val homeDestination: String? = null) : AppDestination

    @Serializable
    data class ListsFeature(val listsDestination: String? = null) : AppDestination

    @Serializable
    data class CategoriesFeature(val categoriesDestination: String? = null) : AppDestination

    @Serializable
    data class LetsPlayFeature(val letsPlayDestination: String? = null) : AppDestination

    @Serializable
    data class ProfileFeature(val profileDestination: String? = null) : AppDestination

    @Serializable
    data class SearchFeature(val searchDestination: String? = null) : AppDestination


    @Serializable
    data class MediaDetailsFeature(val mediaDetailsDestination: String? = null) : AppDestination
}