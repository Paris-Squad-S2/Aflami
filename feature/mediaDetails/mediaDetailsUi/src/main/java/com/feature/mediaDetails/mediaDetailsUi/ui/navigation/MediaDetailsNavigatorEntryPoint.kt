package com.feature.mediaDetails.mediaDetailsUi.ui.navigation

import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface MediaDetailsNavigatorEntryPoint {
    fun navigator(): MediaDetailsNavigator
}

