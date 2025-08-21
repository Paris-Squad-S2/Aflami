package com.paris.domain.game.usecases

import com.google.common.truth.Truth.assertThat
import com.paris.domain.game.entity.UserPoints
import com.paris.domain.game.repositories.GamePointsRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertFailsWith

class UpdatePointsUseCaseTest {

    private lateinit var repository: GamePointsRepository
    private lateinit var useCase: UpdatePointsUseCase

    @BeforeEach
    fun setUp() {
        repository = mockk(relaxed = true)
        useCase = UpdatePointsUseCase(repository)
    }

    @Test
    fun `should add points to existing and save`() = runTest {
        // Given
        coEvery { repository.getUserGamePoints(USER_ID) } returns flowOf(100)
        coEvery { repository.saveUserGamePoints(any()) } returns Unit
        // When
        useCase(USER_ID, 50)
        // Then
        coVerify(exactly = 1) { repository.getUserGamePoints(USER_ID) }
        coVerify(exactly = 1) { repository.saveUserGamePoints(UserPoints(USER_ID, 150)) }
    }

    @Test
    fun `should subtract points when negative and save`() = runTest {
        // Given
        coEvery { repository.getUserGamePoints(USER_ID) } returns flowOf(100)
        coEvery { repository.saveUserGamePoints(any()) } returns Unit
        // When
        useCase(USER_ID, -30)
        // Then
        coVerify(exactly = 1) { repository.saveUserGamePoints(UserPoints(USER_ID, 70)) }
    }

    @Test
    fun `should no-op save when adding zero points`() = runTest {
        // Given
        coEvery { repository.getUserGamePoints(USER_ID) } returns flowOf(100)
        coEvery { repository.saveUserGamePoints(any()) } returns Unit
        // When
        useCase(USER_ID, 0)
        // Then
        coVerify(exactly = 1) { repository.saveUserGamePoints(UserPoints(USER_ID, 100)) }
    }

    @Test
    fun `should propagate when getUserGamePoints throws`() = runTest {
        // Given
        val exception = RuntimeException("DB get error")
        coEvery { repository.getUserGamePoints(USER_ID) } throws exception
        // When & Then
        try {
            useCase(USER_ID, 10)
            throw AssertionError("Exception should have been thrown")
        } catch (e: Exception) {
            assertThat(e).isEqualTo(exception)
        }
    }

    @Test
    fun `should propagate when saveUserGamePoints throws`() = runTest {
        // Given
        coEvery { repository.getUserGamePoints(USER_ID) } returns flowOf(100)
        val exception = RuntimeException("DB save error")
        coEvery { repository.saveUserGamePoints(any()) } throws exception

        // When & Then
        val thrown = assertFailsWith<RuntimeException> {
            useCase(USER_ID, 15)
        }
        assertThat(thrown).isEqualTo(exception)
    }

    private companion object {
        const val USER_ID = 99
    }
}
