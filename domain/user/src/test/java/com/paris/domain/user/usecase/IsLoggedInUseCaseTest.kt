package com.paris.domain.user.usecase

import com.paris.domain.user.repository.UserRepository
import com.paris.domain.user.usecase.auth.IsLoggedInUseCase
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class IsLoggedInUseCaseTest {

    private lateinit var repository: UserRepository
    private lateinit var useCase: IsLoggedInUseCase

    @BeforeEach
    fun setUp() {
        repository = mockk()
        useCase = IsLoggedInUseCase(repository)
    }

    @Test
    fun `should return true when user is logged in`() {
        // Arrange
        every { repository.isLoggedIn() } returns true

        // Act
        val result = useCase()

        // Assert
        assertTrue(result)
    }

    @Test
    fun `should return false when user is not logged in`() {
        // Arrange
        every { repository.isLoggedIn() } returns false

        // Act
        val result = useCase()

        // Assert
        assertFalse(result)
    }
}