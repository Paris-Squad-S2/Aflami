package com.paris.domain.user.usecase

import com.paris.domain.user.repository.UserRepository

class GuestLoginUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): Boolean {
        return userRepository.guestLogin()
    }
}
