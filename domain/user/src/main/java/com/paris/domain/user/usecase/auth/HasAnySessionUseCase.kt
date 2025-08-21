package com.paris.domain.user.usecase.auth

import com.paris.domain.user.repository.UserRepository

class HasAnySessionUseCase(
    private val userRepository: UserRepository
) {
    operator fun invoke(): Boolean {
        return userRepository.hasAnySession()
    }
}