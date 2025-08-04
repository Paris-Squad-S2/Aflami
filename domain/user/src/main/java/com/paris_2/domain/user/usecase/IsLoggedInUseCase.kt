package com.paris_2.domain.user.usecase

import com.paris_2.domain.user.repository.AuthenticationRepository

class IsLoggedInUseCase(
    private val authenticationRepository: AuthenticationRepository
) {
    operator fun invoke(): Boolean {
        return authenticationRepository.isLoggedIn()
    }
}
