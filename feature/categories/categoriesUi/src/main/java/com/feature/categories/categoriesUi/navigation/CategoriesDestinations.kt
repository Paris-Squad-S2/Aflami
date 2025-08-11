package com.feature.categories.categoriesUi.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface Destination

@Serializable
sealed interface Graph : Destination

sealed interface CategoriesDestinations : Graph {

    @Serializable
    data object MainGraph : Graph

    @Serializable
    data object CategoriesScreen : Destination

    @Serializable
    data class CategoryDetailsScreen(val category: String) : Destination
}