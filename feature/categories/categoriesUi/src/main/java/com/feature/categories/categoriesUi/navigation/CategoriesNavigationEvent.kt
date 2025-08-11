package com.feature.categories.categoriesUi.navigation

import androidx.navigation.NavOptions

sealed class CategoriesNavigationEvent {

    data class Navigate(val destination: CategoriesDestination, val navOptions: NavOptions? = null) :
        CategoriesNavigationEvent()

    data object NavigateUp : CategoriesNavigationEvent()
}
