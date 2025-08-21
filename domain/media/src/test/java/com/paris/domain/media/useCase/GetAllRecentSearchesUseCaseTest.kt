package com.paris.domain.media.useCase

import com.paris.domain.media.entity.SearchHistoryModel
import com.paris.domain.media.entity.SearchType
import com.paris.domain.media.repository.SearchHistoryRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import com.google.common.truth.Truth.assertThat

class GetAllRecentSearchesUseCaseTest {

    private lateinit var searchHistoryRepository: SearchHistoryRepository
    private lateinit var getAllRecentSearchesUseCase: GetAllRecentSearchesUseCase

    @BeforeEach
    fun setUp() {
        searchHistoryRepository = mockk()
        getAllRecentSearchesUseCase = GetAllRecentSearchesUseCase(searchHistoryRepository)
    }

    @Test
    fun `should return all recent searches from repository`() = runTest {
        // Given
        coEvery { searchHistoryRepository.getAllSearchHistory() } returns flow {
            emit(recentSearches)
        }

        // When
        val result = getAllRecentSearchesUseCase().first()

        // Then
        assertThat(result).isEqualTo(recentSearches)
    }

    @Test
    fun `should verify repository is called once when returning all recent searches`() = runTest {
        // Given
        coEvery { searchHistoryRepository.getAllSearchHistory() } returns flow {
            emit(recentSearches)
        }

        // When
        getAllRecentSearchesUseCase().first()

        // Then
        coVerify(exactly = 1) { searchHistoryRepository.getAllSearchHistory() }
    }

    @Test
    fun `should return list of correct size when repository has recent searches`() = runTest {
        // Given
        coEvery { searchHistoryRepository.getAllSearchHistory() } returns flow {
            emit(recentSearches)
        }

        // When
        val result = getAllRecentSearchesUseCase().first()

        // Then
        assertThat(result).hasSize(2)
    }

    @Test
    fun `should verify repository is called once when retrieving recent searches`() = runTest {
        // Given
        coEvery { searchHistoryRepository.getAllSearchHistory() } returns flow {
            emit(emptyList())
        }

        // When
        getAllRecentSearchesUseCase().first()

        // Then
        coVerify(exactly = 1) { searchHistoryRepository.getAllSearchHistory() }
    }

    @Test
    fun `should return empty list when repository returns no recent searches`() = runTest {
        // Given
        coEvery { searchHistoryRepository.getAllSearchHistory() } returns flow {
            emit(emptyList())
        }

        // When
        val result = getAllRecentSearchesUseCase().first()

        // Then
        assertThat(result).isEmpty()
    }

    @Test
    fun `should verify repository is called once when repository returns no recent searches`() = runTest {
            // Given
            coEvery { searchHistoryRepository.getAllSearchHistory() } returns flow {
                emit(emptyList())
            }

            // When
            getAllRecentSearchesUseCase().first()

            // Then
            coVerify(exactly = 1) { searchHistoryRepository.getAllSearchHistory() }
        }

    companion object {
        val recentSearches = listOf(
            SearchHistoryModel(
                searchTitle = "Movie1",
                searchDate = "2023-10-01",
                SearchType.Query
            ),
            SearchHistoryModel(
                searchTitle = "Movie2",
                searchDate = "2023-10-01",
                SearchType.Query
            )
        )
    }
}