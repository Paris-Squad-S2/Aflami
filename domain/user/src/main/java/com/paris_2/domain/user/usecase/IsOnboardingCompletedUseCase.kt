package com.paris_2.domain.user.usecase

import com.paris_2.domain.user.repository.SettingRepository

class IsOnboardingCompletedUseCase(
    private val settingRepository: SettingRepository,
) {
    operator fun invoke(): Boolean =
        settingRepository.isOnboardingCompleted()
}