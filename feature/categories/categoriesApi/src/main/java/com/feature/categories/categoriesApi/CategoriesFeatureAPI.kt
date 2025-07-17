package com.feature.categories.categoriesApi

import androidx.compose.runtime.Composable

interface CategoriesFeatureAPI {
    operator fun invoke(categoriesDestination: CategoriesDestination? = null) : @Composable () -> Unit
}