package com.paris.domain.user.usecase

import com.paris.domain.user.repository.SettingRepository

class IsOnboardingCompletedUseCase(
    private val settingRepository: SettingRepository,
) {
    suspend operator fun invoke(): Boolean =
        settingRepository.isOnboardingCompleted()
}