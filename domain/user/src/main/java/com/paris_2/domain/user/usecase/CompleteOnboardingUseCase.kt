package com.paris_2.domain.user.usecase

import com.paris_2.domain.user.repository.SettingRepository


class CompleteOnboardingUseCase(
    private val settingRepository: SettingRepository,
) {
    suspend operator fun invoke() = settingRepository.setOnboardingCompleted()
}