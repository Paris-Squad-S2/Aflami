package com.paris.domain.user.usecase

import com.paris.domain.user.repository.UserRepository

class GetForgetPasswordUrlUseCase(
    private val userRepository: UserRepository
) {
    operator fun invoke(): String {
        return userRepository.getForgetPasswordUrl()
    }
}
