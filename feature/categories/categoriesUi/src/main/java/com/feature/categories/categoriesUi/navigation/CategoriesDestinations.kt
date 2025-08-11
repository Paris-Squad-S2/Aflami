package com.feature.categories.categoriesUi.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface CategoriesDestination

@Serializable
sealed interface CategoriesGraph : CategoriesDestination

sealed interface CategoriesDestinations : CategoriesGraph {
    @Serializable
    data object MainGraph : CategoriesGraph

    @Serializable
    data object CategoriesScreen : CategoriesDestination

    @Serializable
    data object CategoryDetailsScreen : CategoriesDestination
}