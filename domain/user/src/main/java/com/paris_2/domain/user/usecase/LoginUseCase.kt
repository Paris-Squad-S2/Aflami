package com.paris_2.domain.user.usecase

import com.paris_2.domain.user.repository.UserRepository

class LoginUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(username: String, password: String): Boolean {
        return userRepository.login(username, password)
    }

}