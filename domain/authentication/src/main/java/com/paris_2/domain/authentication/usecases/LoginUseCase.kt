package com.paris_2.domain.authentication.usecases

import com.paris_2.domain.authentication.repository.AuthenticationRepository

class LoginUseCase(
    private val authenticationRepository: AuthenticationRepository
) {
    suspend operator fun invoke(username: String, password: String): Boolean {
        return authenticationRepository.login(username, password)
    }

    fun saveSessionId(sessionId: String) {
        authenticationRepository.saveSessionId(sessionId)
    }
}