package com.paris_2.domain.user.usecase

import com.paris_2.domain.user.repository.UserRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(username: String, password: String): Boolean {
        return userRepository.login(username, password)
    }

}