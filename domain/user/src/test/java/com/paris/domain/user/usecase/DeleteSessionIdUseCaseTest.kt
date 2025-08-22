package com.paris.domain.user.usecase

import com.paris.domain.user.repository.UserRepository
import com.paris.domain.user.usecase.auth.DeleteSessionIdUseCase
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class DeleteSessionIdUseCaseTest {
    private lateinit var userRepository: UserRepository
    private lateinit var deleteSessionIdUseCase: DeleteSessionIdUseCase

    @BeforeEach
    fun setUp() {
        userRepository = mockk(relaxed = true)
        deleteSessionIdUseCase = DeleteSessionIdUseCase(userRepository)
    }


    @Test
    fun `should return true when user is logged in`() {
        // Arrange
        every { userRepository.deleteSessionId()} returns true

        // Act
        val result = deleteSessionIdUseCase()

        // Assert
        assertTrue(result)
    }

    @Test
    fun `should return false when user is not logged in`() {
        // Arrange
        every { userRepository.deleteSessionId()} returns false

        // Act
        val result = deleteSessionIdUseCase()

        // Assert
        assertFalse(result)
    }
}