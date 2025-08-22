package com.paris.domain.user.usecase.auth

import com.paris.domain.user.repository.UserRepository

class LoginUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(username: String, password: String): Boolean {
        return userRepository.login(username, password)
    }

}