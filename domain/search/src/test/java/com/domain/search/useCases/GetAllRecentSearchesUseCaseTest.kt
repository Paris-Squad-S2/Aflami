package com.domain.search.useCases

import com.domain.search.model.SearchHistoryModel
import com.domain.search.model.SearchType
import com.domain.search.repository.SearchHistoryRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.first
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
    fun `invoke should return all recent searches from repository`() = runTest {

        //Given
        val recentSearches = listOf(
            SearchHistoryModel(searchTitle = "Movie1", searchDate = "2023-10-01", SearchType.Query),
            SearchHistoryModel(searchTitle = "Movie2", searchDate = "2023-10-01", SearchType.Query)
        )

        coEvery { searchHistoryRepository.getAllSearchHistory() } returns kotlinx.coroutines.flow.flow {
            emit(
                recentSearches
            )
        }

        //When
        val result = getAllRecentSearchesUseCase().first()

        //Then
        assertEquals(2, result.size)
        assertEquals(recentSearches, result)
        coVerify(exactly = 1) { searchHistoryRepository.getAllSearchHistory() }
    }


    @Test
    fun `invoke should return empty list when repository returns no recent searches`() = runTest {

        //Given
        coEvery { searchHistoryRepository.getAllSearchHistory() } returns kotlinx.coroutines.flow.flow {
            emit(
                emptyList()
            )
        }

        //When
        val result = getAllRecentSearchesUseCase().first()

        //Then
        assertTrue(result.isEmpty())
        coVerify(exactly = 1) { searchHistoryRepository.getAllSearchHistory() }
    }

}