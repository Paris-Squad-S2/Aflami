package com.feature.categories.categoriesApi

import androidx.compose.runtime.Composable

interface CategoriesFeatureAPI {
    operator fun invoke() : @Composable () -> Unit
}