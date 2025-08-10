package com.paris_2.domain.user.usecase

import com.paris_2.domain.user.repository.SettingRepository
import javax.inject.Inject

class IsOnboardingCompletedUseCase @Inject constructor(
    private val settingRepository: SettingRepository,
) {
    operator fun invoke(): Boolean =
        settingRepository.isOnboardingCompleted()
}