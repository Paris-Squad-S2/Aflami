package com.domain.user.usecase

import com.domain.user.repository.AuthenticationRepository

class IsLoggedInUseCase(
    private val authenticationRepository: AuthenticationRepository
) {
    operator fun invoke(): Boolean {
        return authenticationRepository.isLoggedIn()
    }
}
