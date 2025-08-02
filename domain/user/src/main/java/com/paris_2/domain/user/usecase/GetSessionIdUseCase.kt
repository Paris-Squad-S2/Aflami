package com.paris_2.domain.user.usecase

import com.paris_2.domain.user.repository.AuthenticationRepository

class GetSessionIdUseCase(
    private val authenticationRepository: AuthenticationRepository
) {
    suspend operator fun invoke(): String? {
        return authenticationRepository.getSessionId()
    }
}