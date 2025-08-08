package com.paris_2.domain.user.usecase

import com.paris_2.domain.user.repository.UserRepository


class CompleteOnboardingUseCase(
    private val authenticationRepository: UserRepository,
) {
    suspend operator fun invoke() = authenticationRepository.setOnboardingCompleted()
}