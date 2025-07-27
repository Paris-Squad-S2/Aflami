package com.paris_2.domain.authentication.usecase

import com.paris_2.domain.authentication.repository.AuthenticationRepository

class IsLoggedInUseCase(
    private val authenticationRepository: AuthenticationRepository
) {
    operator fun invoke(): Boolean {
        return authenticationRepository.isLoggedIn()
    }
}
