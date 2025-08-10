package com.paris_2.domain.user.usecase

import com.paris_2.domain.user.repository.UserRepository
import javax.inject.Inject

class GuestLoginUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): Boolean {
        return userRepository.guestLogin()
    }
}
