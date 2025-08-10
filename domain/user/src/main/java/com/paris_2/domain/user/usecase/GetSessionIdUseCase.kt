package com.paris_2.domain.user.usecase

import com.paris_2.domain.user.repository.UserRepository
import javax.inject.Inject

class GetSessionIdUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): String? {
        return userRepository.getSessionId()
    }
}