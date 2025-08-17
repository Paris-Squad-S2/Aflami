package com.feature.guessGame.guessGameUi.navigation

import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@EntryPoint
@InstallIn(SingletonComponent::class)
interface GuessGameNavigatorEntryPoint {
    fun guessGameNavigator(): GuessGameNavigator
}