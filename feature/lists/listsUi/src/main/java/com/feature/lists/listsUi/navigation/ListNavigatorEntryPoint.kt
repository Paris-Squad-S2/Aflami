package com.feature.lists.listsUi.navigation

import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface ListNavigatorEntryPoint {
    fun ListNavigator(): ListNavigator
}