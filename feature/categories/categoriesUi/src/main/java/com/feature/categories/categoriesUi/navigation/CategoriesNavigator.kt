package com.feature.categories.categoriesUi.navigation

import androidx.navigation.NavOptions
import kotlinx.coroutines.flow.Flow

interface CategoriesNavigator {
    val startGraph: Graph
    val categoriesNavigationEvent: Flow<CategoriesNavigationEvent>
    suspend fun navigate(destination: Destination, navOptions: NavOptions? = null)
    suspend fun navigateUp()
}
