package com.paris.domain.user.usecase.auth

import com.paris.domain.user.repository.UserRepository

class GetSessionIdUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): String? {
        return userRepository.getSessionId()
    }
}