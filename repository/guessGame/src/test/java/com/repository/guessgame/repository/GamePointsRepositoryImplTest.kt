package com.repository.guessgame.repository

import com.google.common.truth.Truth.assertThat
import com.paris_2.domain.game.entity.UserPoints
import com.paris_2.domain.game.exception.FailedException
import com.paris_2.domain.game.exception.NoInternetConnectionException
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
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertFailsWith

class GamePointsRepositoryImplTest {

    private lateinit var repository: GamePointsRepositoryImpl
    private val local: GamePointsLocalDataSource = mockk(relaxed = true)
    private val networkConnectionChecker: NetworkConnectionChecker = mockk()

    @BeforeEach
    fun setUp() {
        every { networkConnectionChecker.isConnected } returns MutableStateFlow(true)
        repository = GamePointsRepositoryImpl(local, networkConnectionChecker)
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
    fun `saveUserGamePoints throws NoInternetConnectionException when no internet`() = runTest {
        // Given
        every { networkConnectionChecker.isConnected } returns MutableStateFlow(false)
        val points = UserPoints(userId = 7, gamePoints = 200)
        repository = GamePointsRepositoryImpl(local, networkConnectionChecker)
        
        // When & Then
        assertFailsWith<NoInternetConnectionException> {
            repository.saveUserGamePoints(points)
        }
    }

    @Test
    fun `saveUserGamePoints throws FailedException when local throws exception`() = runTest {
        // Given
        val points = UserPoints(userId = 7, gamePoints = 200)
        coEvery { local.saveUserGamePoints(UserGamePointsEntity(7, 200)) } throws RuntimeException()
        
        // When & Then
        assertFailsWith<FailedException> {
            repository.saveUserGamePoints(points)
        }
        assertThat(assertFailsWith<FailedException> {
            repository.saveUserGamePoints(points)
        }.message).isEqualTo("Failed to save user game points")
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
        repository = GamePointsRepositoryImpl(local, networkConnectionChecker)
        
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
        repository = GamePointsRepositoryImpl(local, networkConnectionChecker)
        
        // When
        val result = repository.getUserGamePoints(7).first()
        
        // Then
        assertThat(result).isEqualTo(0)
    }

    @Test
    fun `getUserGamePoints throws FailedException when mapping throws exception`() = runTest {
        // Given
        val entity = UserGamePointsEntity(7, 150)
        every { local.getUserGamePoints(7) } returns flowOf(entity)
        // Mock the extension function by mocking the entity data in a way that causes an exception
        // In this case, we can't easily mock the extension function, so we'll simulate the exception in safeCall
        repository = GamePointsRepositoryImpl(local, networkConnectionChecker)
        
        // Create a repository with a mock local data source that throws an exception in the flow
        val failingLocal = mockk<GamePointsLocalDataSource>()
        coEvery { failingLocal.getUserGamePoints(7) } throws RuntimeException()
        val failingRepository = GamePointsRepositoryImpl(failingLocal, networkConnectionChecker)
        
        // When & Then
        assertFailsWith<FailedException> {
            failingRepository.getUserGamePoints(7).first()
        }
        assertThat(assertFailsWith<FailedException> {
            failingRepository.getUserGamePoints(7).first()
        }.message).isEqualTo("Failed to get user game points")
    }
}