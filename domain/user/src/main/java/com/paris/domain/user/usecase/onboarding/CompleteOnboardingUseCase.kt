package com.paris.domain.user.usecase.onboarding

import com.paris.domain.user.repository.SettingRepository


class CompleteOnboardingUseCase(
    private val settingRepository: SettingRepository,
) {
    suspend operator fun invoke() = settingRepository.setOnboardingCompleted()
}