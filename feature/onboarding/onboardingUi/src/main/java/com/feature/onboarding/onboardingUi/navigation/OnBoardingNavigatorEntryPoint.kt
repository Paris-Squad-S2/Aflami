package com.feature.onboarding.onboardingUi.navigation

import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface OnBoardingNavigatorEntryPoint {
    fun onBoardingNavigator(): OnBoardingNavigator
}