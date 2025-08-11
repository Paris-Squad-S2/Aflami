package com.feature.categories.categoriesUi.navigation

import androidx.navigation.NavOptions
import kotlinx.coroutines.flow.Flow

interface CategoriesNavigator {
    val startGraph: CategoriesGraph
    val categoriesNavigationEvent: Flow<CategoriesNavigationEvent>
    suspend fun navigate(destination: CategoriesDestination, navOptions: NavOptions? = null)
    suspend fun navigateUp()
}
