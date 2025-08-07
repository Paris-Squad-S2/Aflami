package com.feature.onboarding.onboardingUi

import androidx.compose.runtime.Composable
import com.feature.onboarding.onboardingApi.OnBoardingFeatureAPI
import com.feature.onboarding.onboardingUi.navigation.OnBoardingDestinations
import com.feature.onboarding.onboardingUi.navigation.OnBoardingNavGraph
import com.feature.onboarding.onboardingUi.navigation.OnBoardingNavigatorImpl

class OnBoardingFeatureAPIImpl : OnBoardingFeatureAPI {
    override fun invoke(): @Composable () -> Unit {
        return {
            val navigator = OnBoardingNavigatorImpl(OnBoardingDestinations.OnBoardingGraph1)
            OnBoardingNavGraph(navigator = navigator)
        }
    }
}