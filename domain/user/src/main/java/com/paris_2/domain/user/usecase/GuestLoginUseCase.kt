package com.paris_2.domain.user.usecase

import com.paris_2.domain.user.repository.UserRepository

class GuestLoginUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): Boolean {
        return userRepository.guestLogin()
    }
}
