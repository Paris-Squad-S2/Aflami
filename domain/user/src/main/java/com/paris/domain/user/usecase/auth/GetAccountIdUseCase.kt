package com.paris.domain.user.usecase.auth

import com.paris.domain.user.repository.UserRepository

class GetAccountIdUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): Int? {
        return userRepository.getAccountId()
    }
}