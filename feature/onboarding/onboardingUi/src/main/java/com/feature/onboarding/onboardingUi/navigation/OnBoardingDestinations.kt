package com.feature.onboarding.onboardingUi.navigation

import kotlinx.serialization.Serializable

sealed interface OnBoardingDestinations : OnBoardingGraph {
    @Serializable
    data object OnBoardingGraph1 : OnBoardingGraph

    @Serializable
    data object OnBoardingScreen : OnBoardingDestination

    @Serializable
    data object HomeScreen : OnBoardingDestination
}
