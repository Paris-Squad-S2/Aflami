package com.feature.onboarding.onboardingUi.navigation


import androidx.navigation.NavOptions
import kotlinx.coroutines.flow.Flow

interface OnBoardingNavigator {
    val startGraph: OnBoardingGraph
    val onboardingNavigationEvent: Flow<OnBoardingNavigationEvent>
    suspend fun navigate(destination: OnBoardingDestination, navOptions: NavOptions? = null)
    suspend fun navigateUp()
}
