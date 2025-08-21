package com.paris.domain.user.usecase

import com.paris.domain.user.repository.UserRepository

class GetAccountIdUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): Int? {
        return userRepository.getAccountId()
    }
}