package com.paris.domain.user.usecase.auth

import com.paris.domain.user.repository.UserRepository

class GetForgetPasswordUrlUseCase(
    private val userRepository: UserRepository
) {
    operator fun invoke(): String {
        return userRepository.getForgetPasswordUrl()
    }
}
