package com.feature.onboarding.onboardingUi

import android.content.Context
import android.content.Intent
import com.feature.onboarding.onboardingApi.OnBoardingFeatureAPI

class OnBoardingFeatureAPIImpl(
    private val context: Context
) : OnBoardingFeatureAPI {
    override fun invoke() {
        val intent = Intent(context, OnboardingActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        context.startActivity(intent)
    }
}