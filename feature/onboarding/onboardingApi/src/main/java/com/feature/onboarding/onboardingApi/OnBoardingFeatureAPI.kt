package com.feature.onboarding.onboardingApi
import androidx.compose.runtime.Composable

interface OnBoardingFeatureAPI {
    operator fun invoke() : @Composable () -> Unit
}