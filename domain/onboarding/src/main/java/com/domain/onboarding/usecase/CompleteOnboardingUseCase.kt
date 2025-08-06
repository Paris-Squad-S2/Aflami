package com.domain.onboarding.usecase

import com.domain.onboarding.repository.OnboardingRepository


class CompleteOnboardingUseCase(
    private val repository: OnboardingRepository,
) {
    suspend operator fun invoke() = repository.setOnboardingCompleted()
}