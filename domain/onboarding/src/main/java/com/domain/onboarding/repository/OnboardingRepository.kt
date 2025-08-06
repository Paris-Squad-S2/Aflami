package com.domain.onboarding.repository


interface OnboardingRepository {
    suspend fun setOnboardingCompleted()
}
