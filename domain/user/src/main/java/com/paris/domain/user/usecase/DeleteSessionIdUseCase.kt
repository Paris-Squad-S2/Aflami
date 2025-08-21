package com.paris.domain.user.usecase

import com.paris.domain.user.repository.UserRepository

class DeleteSessionIdUseCase(
    private val userRepository: UserRepository,
) {
    operator fun invoke(): Boolean {
       return userRepository.deleteSessionId()
    }
}