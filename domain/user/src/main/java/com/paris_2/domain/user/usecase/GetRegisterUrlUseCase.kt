package com.paris_2.domain.user.usecase

import com.paris_2.domain.user.repository.UserRepository
import javax.inject.Inject

class GetRegisterUrlUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(): String {
        return userRepository.getRegisterUrl()
    }
}
