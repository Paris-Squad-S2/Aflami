
package com.datasource.local.guessGame.datasource

import com.datasource.local.guessGame.dao.GamePointsDao
import com.google.common.truth.Truth.assertThat
import com.repository.guessgame.entity.UserGamePointsEntity
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GamePointsLocalDataSourceImplTest {
    private lateinit var dataSource: GamePointsLocalDataSourceImpl
    private val dao: GamePointsDao = mockk(relaxed = false)

    @BeforeEach
    fun setUp() {
        dataSource = GamePointsLocalDataSourceImpl(dao)
    }

    @Test
    fun `getUserGamePoints should return entity when DAO returns value`() = runTest {
        // Given
        coEvery { dao.getUserGamePoints(USER_ID) } returns flowOf(samplePoints)
        // When
        val result = dataSource.getUserGamePoints(USER_ID).first()
        // Then
        assertThat(result).isEqualTo(samplePoints)
    }

    @Test
    fun `getUserGamePoints should return null when DAO returns null`() = runTest {
        // Given
        coEvery { dao.getUserGamePoints(USER_ID) } returns flowOf(null)
        // When
        val result = dataSource.getUserGamePoints(USER_ID).first()
        // Then
        Assertions.assertNull(result)
    }

    @Test
    fun `saveUserGamePoints should upsert when called`() = runTest {
        // Given
        coEvery { dao.upsertUserGamePoints(any()) } returns Unit
        // When
        dataSource.saveUserGamePoints(samplePoints)
        // Then
        coVerify(exactly = 1) { dao.upsertUserGamePoints(samplePoints) }
    }

    private companion object {
        const val USER_ID = 42
        val samplePoints = UserGamePointsEntity(
            userId = USER_ID,
            gamePoints = 150
        )
    }
}
