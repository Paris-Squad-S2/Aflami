package com.paris.domain.user.usecase.auth

import com.paris.domain.user.repository.UserRepository

class GetRegisterUrlUseCase(
    private val userRepository: UserRepository
) {
    operator fun invoke(): String {
        return userRepository.getRegisterUrl()
    }
}
