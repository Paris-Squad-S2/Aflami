package com.paris_2.domain.authentication.usecase

import com.paris_2.domain.authentication.repository.AuthenticationRepository

class HasAnySessionUseCase(
    private val authenticationRepository: AuthenticationRepository
) {
    operator fun invoke(): Boolean {
        return authenticationRepository.hasAnySession()
    }
}