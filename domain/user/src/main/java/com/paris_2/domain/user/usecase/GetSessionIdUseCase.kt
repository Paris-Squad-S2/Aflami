package com.paris_2.domain.user.usecase

import com.paris_2.domain.user.repository.UserRepository

class GetSessionIdUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): String? {
        return userRepository.getSessionId()
    }
}