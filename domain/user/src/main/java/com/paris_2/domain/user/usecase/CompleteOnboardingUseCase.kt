package com.paris_2.domain.user.usecase

import com.paris_2.domain.user.repository.SettingRepository
import javax.inject.Inject


class CompleteOnboardingUseCase @Inject constructor(
    private val settingRepository: SettingRepository,
) {
    suspend operator fun invoke() = settingRepository.setOnboardingCompleted()
}