package com.paris_2.domain.user.usecase

import com.paris_2.domain.user.repository.AuthenticationRepository

class GetForgetPasswordUrlUseCase(
    private val authenticationRepository: AuthenticationRepository
) {
    operator fun invoke(): String {
        return authenticationRepository.getForgetPasswordUrl()
    }
}
