package com.paris_2.domain.authentication.usecases

import com.paris_2.domain.authentication.repository.AuthenticationRepository

class GetRegisterUrlUseCase(
    private val authenticationRepository: AuthenticationRepository
) {
    operator fun invoke(): String {
        return authenticationRepository.getRegisterUrl()
    }
}
