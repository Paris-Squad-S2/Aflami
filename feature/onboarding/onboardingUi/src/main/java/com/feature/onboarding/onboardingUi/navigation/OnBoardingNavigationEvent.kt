package com.feature.onboarding.onboardingUi.navigation


import androidx.navigation.NavOptions

sealed class OnBoardingNavigationEvent {
    data class Navigate(val destination: OnBoardingDestination, val navOptions: NavOptions? = null) : OnBoardingNavigationEvent()
    data object NavigateUp : OnBoardingNavigationEvent()
}
