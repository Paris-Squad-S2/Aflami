package com.feature.profile.profileUi.navigation

import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface ProfileNavigatorEntryPoint {
    fun profileNavigator(): ProfileNavigator
}