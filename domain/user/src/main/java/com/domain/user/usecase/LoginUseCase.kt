package com.domain.user.usecase

import com.domain.user.repository.AuthenticationRepository

class LoginUseCase(
    private val authenticationRepository: AuthenticationRepository
) {
    suspend operator fun invoke(username: String, password: String): Boolean {
        return authenticationRepository.login(username, password)
    }

}