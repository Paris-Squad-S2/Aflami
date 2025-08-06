package com.paris_2.domain.user.usecase

import com.paris_2.domain.user.repository.UserRepository

class GetAccountIdUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): Int? {
        return userRepository.getAccountId()
    }
}