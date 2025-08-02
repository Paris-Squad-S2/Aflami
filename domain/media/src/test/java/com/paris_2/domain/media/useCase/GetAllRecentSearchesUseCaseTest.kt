package com.paris_2.domain.media.useCase

import com.domain.media.entity.SearchHistoryModel
import com.domain.media.entity.SearchType
import com.domain.media.repository.SearchHistoryRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

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
        assertEquals(recentSearches, result)
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
    fun `should return list of correct size when repository has recent searches`() =
        runTest {
            // Given
            coEvery { searchHistoryRepository.getAllSearchHistory() } returns flow {
                emit(recentSearches)
            }

            // When
            val result = getAllRecentSearchesUseCase().first()

            // Then
            assertEquals(2, result.size)
        }

    @Test
    fun `should verify repository is called once when retrieving recent searches`() =
        runTest {

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

        //Given
        coEvery { searchHistoryRepository.getAllSearchHistory() } returns flow {
            emit(
                emptyList()
            )
        }

        //When
        val result = getAllRecentSearchesUseCase().first()

        //Then
        assertTrue(result.isEmpty())
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

    companion object{
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