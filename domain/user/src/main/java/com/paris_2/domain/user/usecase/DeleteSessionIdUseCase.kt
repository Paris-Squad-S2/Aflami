package com.paris_2.domain.user.usecase

import com.paris_2.domain.user.repository.UserRepository
import javax.inject.Inject

class DeleteSessionIdUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    operator fun invoke(): Boolean {
       return userRepository.deleteSessionId()
    }
}