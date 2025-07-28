package com.paris_2.domain.authentication.usecase

import com.paris_2.domain.authentication.repository.AuthenticationRepository

class GetSessionIdUseCase(
    private val authenticationRepository: AuthenticationRepository
) {
    operator fun invoke(): String? {
        return authenticationRepository.getSessionId()
    }
}