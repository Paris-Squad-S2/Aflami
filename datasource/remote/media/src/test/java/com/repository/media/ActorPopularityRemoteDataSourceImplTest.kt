package com.repository.media

import com.google.common.truth.Truth
import com.repository.guessgame.dto.ActorDto
import com.repository.guessgame.dto.ActorPopularityListDto
import com.repository.media.services.ActorPopularityApiService
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class ActorPopularityRemoteDataSourceImplTest {
    private lateinit var apiService: ActorPopularityApiService
    private lateinit var dataSource: ActorPopularityRemoteDataSourceImpl

    @Before
    fun setUp() {
        apiService = mockk(relaxed = true)
        dataSource = ActorPopularityRemoteDataSourceImpl(apiService)
    }

    @Test
    fun `getPopularActors should propagate exception when API call fails`() = runTest {
        // Given
        val apiException = RuntimeException("API Error")
        coEvery { apiService.getPopularActors( any()) } throws apiException

        // When & Then
        try {
            dataSource.getPopularActors()
            throw AssertionError("Should have propagated the exception")
        } catch (e: Exception) {
            Truth.assertThat(e).isEqualTo(apiException)
        }
    }

    @Test
    fun `getPopularActors should return actors from API`() = runTest {
        // Given
        coEvery { apiService.getPopularActors(any()) } returns expectedActors
        // When
        val result = dataSource.getPopularActors()
        // Then
        Truth.assertThat(result).isEqualTo(expectedActors)
    }

    @Test
    fun `getPopularActors should call service once with valid page`() = runTest {
        // Given
        coEvery { apiService.getPopularActors( any()) } returns expectedActors
        // When
        dataSource.getPopularActors()
        // Then
        coVerify(exactly = 1) { apiService.getPopularActors( match { it in 1..499 }) }
    }

    private companion object {
        const val LANGUAGE = "en"
        val expectedActors = ActorPopularityListDto(
            page = 1,
            totalPages = 10,
            totalResults = 200,
            results = listOf(
                ActorDto(id = 1, name = "Actor One", popularity = 123.4),
                ActorDto(id = 2, name = "Actor Two", popularity = 98.7)
            )
        )
    }
}