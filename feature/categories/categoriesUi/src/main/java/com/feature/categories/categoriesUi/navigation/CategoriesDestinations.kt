package com.feature.categories.categoriesUi.navigation

import kotlinx.serialization.Serializable

sealed interface CategoriesDestinations : CategoriesGraph {
    @Serializable
    data object CategoriesGraph1 : CategoriesGraph

    @Serializable
    data object CategoriesScreen : CategoriesDestination
}