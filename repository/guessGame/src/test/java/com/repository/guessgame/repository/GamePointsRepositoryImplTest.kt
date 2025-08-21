package com.repository.guessgame.repository

import com.google.common.truth.Truth.assertThat
import com.paris.domain.game.entity.UserPoints
import com.paris.domain.game.exception.FailedException
import com.paris.domain.game.exception.NoInternetConnectionException
import com.repository.guessgame.datasource.local.GamePointsLocalDataSource
import com.repository.guessgame.entity.UserGamePointsEntity
import com.repository.guessgame.utils.NetworkConnectionChecker
import com.repository.guessgame.mapper.toDomain
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertFailsWith

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
    fun `saveUserGamePoints throws FailedException when local throws exception`() = runTest {
        // Given
        val points = UserPoints(userId = 7, gamePoints = 200)
        coEvery { local.saveUserGamePoints(UserGamePointsEntity(7, 200)) } throws RuntimeException()
        
        // When & Then
        val exception = assertFailsWith<FailedException> {
            repository.saveUserGamePoints(points)
        }
        assertThat(exception.message).isEqualTo("Failed to save user game points")
    }

    @Test
    fun `getUserGamePoints returns mapped domain from local`() = runTest {
        // Given
        every { local.getUserGamePoints(7) } returns flowOf(
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

    @Test
    fun `getUserGamePoints returns zero when entity is null`() = runTest {
        // Given
        every { local.getUserGamePoints(7) } returns flowOf(null)
        
        // When
        val result = repository.getUserGamePoints(7).first()
        
        // Then
        assertThat(result).isEqualTo(0)
    }

    @Test
    fun `getUserGamePoints throws FailedException when local throws exception`() = runTest {
        // Given
        every { local.getUserGamePoints(7) } returns flow {
            throw RuntimeException()
        }
        
        // When & Then
        val exception = assertFailsWith<FailedException> {
            repository.getUserGamePoints(7).first()
        }
        assertThat(exception.message).isEqualTo("Failed to get user game points")
    }
}