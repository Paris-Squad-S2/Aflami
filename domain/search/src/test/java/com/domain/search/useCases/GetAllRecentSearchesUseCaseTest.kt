package com.domain.search.useCases

import com.domain.search.model.SearchHistoryModel
import com.domain.search.repository.SearchHistoryRepository
import io.mockk.coEvery
import io.mockk.mockk
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
            SearchHistoryModel(id = 1, searchTitle = "Movie1"),
            SearchHistoryModel(id = 2, searchTitle = "Movie2")
        )

        coEvery { searchHistoryRepository.getAllSearchHistory() } returns recentSearches

        //When
        val result = getAllRecentSearchesUseCase()

        //Then
        assertEquals(2, result.size)
        assertEquals(recentSearches, result)
    }


    @Test
    fun `invoke should return empty list when repository returns no recent searches`() = runTest {

        //Given
        coEvery { searchHistoryRepository.getAllSearchHistory() } returns emptyList()

        //When
        val result = getAllRecentSearchesUseCase()

        //Then
        assertTrue { result.isEmpty() }
    }

}