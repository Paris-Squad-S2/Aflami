package com.domain.user.usecase

import com.domain.user.repository.AuthenticationRepository

class GetRegisterUrlUseCase(
    private val authenticationRepository: AuthenticationRepository
) {
    operator fun invoke(): String {
        return authenticationRepository.getRegisterUrl()
    }
}
