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

class GetUserPointUseCaseTest {

    private lateinit var repository: GamePointsRepository
    private lateinit var useCase: GetUserPointUseCase

    @BeforeEach
    fun setUp() {
        repository = mockk()
        useCase = GetUserPointUseCase(repository)
    }

    @Test
    fun `should return user points from repository`() = runTest {
        // Given
        coEvery { repository.getUserGamePoints(USER_ID) } returns sampleUserPoints
        // When
        val result = useCase(USER_ID)
        // Then
        assertThat(result).isEqualTo(sampleUserPoints.gamePoints)
    }

    @Test
    fun `should call getUserGamePoints exactly once with correct id`() = runTest {
        // Given
        coEvery { repository.getUserGamePoints(USER_ID) } returns sampleUserPoints
        // When
        useCase(USER_ID)
        // Then
        coVerify(exactly = 1) { repository.getUserGamePoints(USER_ID) }
    }

    @Test
    fun `should propagate exception when repository throws`() = runTest {
        // Given
        val exception = RuntimeException("DB error")
        coEvery { repository.getUserGamePoints(USER_ID) } throws exception
        // When & Then
        try {
            useCase(USER_ID)
            throw AssertionError("Exception should have been thrown")
        } catch (e: Exception) {
            assertThat(e).isEqualTo(exception)
        }
    }

    private companion object {
        const val USER_ID = 7
        val sampleUserPoints = UserPoints(userId = USER_ID, gamePoints = 250)
    }
}
