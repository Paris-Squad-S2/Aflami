package com.repository.search.repository

import com.repository.search.dataSource.local.HistoryLocalDataSource
import com.repository.search.entity.SearchHistoryEntity
import com.repository.search.entity.SearchType
import com.repository.search.mapper.toSearchHistories
import io.mockk.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SearchHistoryRepositoryImplTest {

    private lateinit var repository: SearchHistoryRepositoryImpl
    private val historyLocalDataSource = mockk<HistoryLocalDataSource>()

    @BeforeEach
    fun setUp() {
        repository = SearchHistoryRepositoryImpl(historyLocalDataSource)
    }

    @Test
    fun `getAllSearchHistory should return mapped history from local data source`() = runTest {
        // Given
        val localHistory = listOf(SearchHistoryEntity("action", SearchType.Query))
        coEvery { historyLocalDataSource.getAllSearchQueries() } returns flowOf(localHistory)

        // When
        val result = repository.getAllSearchHistory().first()

        // Then
        assertEquals(flowOf(localHistory).toSearchHistories().first(), result)
    }

    @Test
    fun `addSearchHistory should call addSearchQuery with correct title`() = runTest {
        // Given
        val title = "comedy"
        coEvery { historyLocalDataSource.addSearchQuery(title, SearchType.Query) } just Runs

        // When
        repository.addSearchHistory(title, com.domain.search.model.SearchType.Query)

        // Then
        coVerify(exactly = 1) { historyLocalDataSource.addSearchQuery(title, SearchType.Query) }
    }

    @Test
    fun `clearSearchHistory should call clearSearchQueryByQuery with correct query`() = runTest {
        // Given
        val query = "drama"
        coEvery { historyLocalDataSource.clearSearchQueryByQuery(query, SearchType.Query) } just Runs

        // When
        repository.clearSearchHistory(query, com.domain.search.model.SearchType.Query)

        // Then
        coVerify(exactly = 1) { historyLocalDataSource.clearSearchQueryByQuery(query, SearchType.Query) }
    }

    @Test
    fun `clearAllSearchHistory should call clearAll`() = runTest {
        // Given
        coEvery { historyLocalDataSource.clearAll() } just Runs

        // When
        repository.clearAllSearchHistory()

        // Then
        coVerify(exactly = 1) { historyLocalDataSource.clearAll() }
    }
}
