package com.paris_2.domain.user.usecase

import com.paris_2.domain.user.repository.UserRepository

class IsOnboardingCompletedUseCase(
    private val authenticationRepository: UserRepository,
) {
    operator fun invoke(): Boolean =
        authenticationRepository.isOnboardingCompleted()
}