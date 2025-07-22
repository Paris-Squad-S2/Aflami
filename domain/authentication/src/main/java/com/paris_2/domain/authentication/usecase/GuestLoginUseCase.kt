package com.paris_2.domain.authentication.usecase

import com.paris_2.domain.authentication.repository.AuthenticationRepository

class GuestLoginUseCase(
    private val authenticationRepository: AuthenticationRepository
) {
    suspend operator fun invoke(): Boolean {
        return authenticationRepository.guestLogin()
    }
}
