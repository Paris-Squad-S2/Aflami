package com.domain.user.usecase

import com.domain.user.repository.AuthenticationRepository

class HasAnySessionUseCase(
    private val authenticationRepository: AuthenticationRepository
) {
    operator fun invoke(): Boolean {
        return authenticationRepository.hasAnySession()
    }
}