package com.paris_2.domain.user.usecase

import com.paris_2.domain.user.repository.AuthenticationRepository


class CompleteOnboardingUseCase(
    private val authenticationRepository: AuthenticationRepository,
) {
    suspend operator fun invoke() = authenticationRepository.setOnboardingCompleted()
}