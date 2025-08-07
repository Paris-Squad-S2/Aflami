package com.paris_2.domain.user.usecase

import com.paris_2.domain.user.repository.AuthenticationRepository

class IsOnboardingCompletedUseCase(
    private val authenticationRepository: AuthenticationRepository,
) {
    operator fun invoke(): Boolean =
        authenticationRepository.isOnboardingCompleted()
}