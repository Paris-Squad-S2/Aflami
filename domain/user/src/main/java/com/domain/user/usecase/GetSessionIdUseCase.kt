package com.domain.user.usecase

import com.domain.user.repository.AuthenticationRepository

class GetSessionIdUseCase(
    private val authenticationRepository: AuthenticationRepository
) {
    suspend operator fun invoke(): String? {
        return authenticationRepository.getSessionId()
    }
}