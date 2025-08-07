package com.feature.onboarding.onboardingUi

import androidx.compose.runtime.Composable
import com.feature.onboarding.onboardingApi.OnBoardingFeatureAPI
import com.feature.onboarding.onboardingUi.navigation.OnBoardingNavGraph

class OnBoardingFeatureAPIImpl(): OnBoardingFeatureAPI {
    override fun invoke(): @Composable () -> Unit {
        return {
            OnBoardingNavGraph()
        }
    }
}