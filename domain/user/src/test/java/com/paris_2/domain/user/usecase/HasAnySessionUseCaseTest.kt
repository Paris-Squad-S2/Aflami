package com.paris_2.domain.user.usecase

import com.paris_2.domain.user.repository.UserRepository
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class HasAnySessionUseCaseTest {

    private lateinit var userRepository: UserRepository
    private lateinit var hasAnySessionUseCase: HasAnySessionUseCase

    @BeforeEach
    fun setup() {
        userRepository = mockk()
        hasAnySessionUseCase = HasAnySessionUseCase(userRepository)
    }

    @Test
    fun `should return true when there is a session`() {
        // Arrange
        every { userRepository.hasAnySession() } returns true

        // Act
        val result = hasAnySessionUseCase()

        // Assert
        assertTrue(result)
    }

    @Test
    fun `should return false when there is no session`() {
        // Arrange
        every { userRepository.hasAnySession() } returns false

        // Act
        val result = hasAnySessionUseCase()

        // Assert
        assertFalse(result)
    }
}