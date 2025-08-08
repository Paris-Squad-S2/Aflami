package com.paris_2.domain.user.usecase

import com.paris_2.domain.user.repository.UserRepository

class GetForgetPasswordUrlUseCase(
    private val userRepository: UserRepository
) {
    operator fun invoke(): String {
        return userRepository.getForgetPasswordUrl()
    }
}
