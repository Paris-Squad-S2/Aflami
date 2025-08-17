package com.repository.guessgame.repository

import com.google.common.truth.Truth.assertThat
import com.paris_2.domain.game.entity.UserPoints
import com.repository.guessgame.datasource.local.GamePointsLocalDataSource
import com.repository.guessgame.entity.UserGamePointsEntity
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GamePointsRepositoryImplTest {

    private lateinit var repository: GamePointsRepositoryImpl
    private val local: GamePointsLocalDataSource = mockk(relaxed = true)

    @BeforeEach
    fun setUp() {
        repository = GamePointsRepositoryImpl(local)
    }

    @Test
    fun `saveUserGamePoints delegates to local with mapped entity`() = runTest {
        // Given
        val points = UserPoints(userId = 7, gamePoints = 200)
        coEvery { local.saveUserGamePoints(UserGamePointsEntity(7, 200)) } returns Unit
        // When
        repository.saveUserGamePoints(points)
        // Then
        coVerify(exactly = 1) { local.saveUserGamePoints(UserGamePointsEntity(7, 200)) }
    }

    @Test
    fun `getUserGamePoints returns mapped domain from local`() = runTest {
        // Given
        coEvery { local.getUserGamePoints(7) } returns flowOf(
            UserGamePointsEntity(
                userId = 7,
                gamePoints = 150
            )
        )
        // When
        val result = repository.getUserGamePoints(7).first()
        // Then
        assertThat(result).isEqualTo(150)
        coVerify(exactly = 1) { local.getUserGamePoints(7) }
    }
}
