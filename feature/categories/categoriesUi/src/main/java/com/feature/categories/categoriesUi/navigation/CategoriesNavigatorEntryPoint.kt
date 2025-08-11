package com.feature.categories.categoriesUi.navigation

import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface CategoriesNavigatorEntryPoint {
    fun categoriesNavigator(): CategoriesNavigator
}
