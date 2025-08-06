package com.datasource.local.onboarding


import com.domain.onboarding.repository.OnboardingRepository

class OnboardingRepositoryImpl(
    private val preferences: OnboardingPreferences,
) : OnboardingRepository {
    override suspend fun setOnboardingCompleted() {
        preferences.setOnboardingCompleted()
    }
}
