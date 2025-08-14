package com.paris_2.domain.game.usecases

import com.google.common.truth.Truth.assertThat
import com.paris_2.domain.game.entity.UserPoints
import com.paris_2.domain.game.repositories.GamePointsRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SaveUserGamePointsUseCaseTest {

    private lateinit var repository: GamePointsRepository
    private lateinit var useCase: SaveUserGamePointsUseCase

    @BeforeEach
    fun setUp() {
        repository = mockk(relaxed = true)
        useCase = SaveUserGamePointsUseCase(repository)
    }

    @Test
    fun `should call repository to save user points`() = runTest {
        // Given
        val points = UserPoints(userId = 1, gamePoints = 300)
        coEvery { repository.saveUserGamePoints(points) } returns Unit
        // When
        useCase(points)
        // Then
        coVerify(exactly = 1) { repository.saveUserGamePoints(points) }
    }

    @Test
    fun `should propagate exception when repository throws`() = runTest {
        // Given
        val points = UserPoints(userId = 2, gamePoints = 150)
        val exception = RuntimeException("DB error")
        coEvery { repository.saveUserGamePoints(points) } throws exception
        // When & Then
        try {
            useCase(points)
            throw AssertionError("Exception should have been thrown")
        } catch (e: Exception) {
            assertThat(e).isEqualTo(exception)
        }
    }
}
