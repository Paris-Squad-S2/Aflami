package com.domain.user.usecase

import com.domain.user.repository.AuthenticationRepository

class GuestLoginUseCase(
    private val authenticationRepository: AuthenticationRepository
) {
    suspend operator fun invoke(): Boolean {
        return authenticationRepository.guestLogin()
    }
}
