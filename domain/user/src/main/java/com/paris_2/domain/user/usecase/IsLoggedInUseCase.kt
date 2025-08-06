package com.paris_2.domain.user.usecase

import com.paris_2.domain.user.repository.UserRepository

class IsLoggedInUseCase(
    private val userRepository: UserRepository
) {
    operator fun invoke(): Boolean {
        return userRepository.isLoggedIn()
    }
}
