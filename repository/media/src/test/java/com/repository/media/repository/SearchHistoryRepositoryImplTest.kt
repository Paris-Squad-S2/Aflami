package com.repository.media.repository

import com.repository.media.datasource.local.HistoryLocalDataSource
import com.repository.media.entity.SearchHistoryEntity
import com.repository.media.entity.SearchType
import com.repository.media.mapper.search.toSearchHistories
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
        coEvery { historyLocalDataSource.getSearchQueries() } returns flowOf(localHistory)

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
        repository.addSearchHistory(title, com.paris_2.domain.media.entity.SearchType.Query)

        // Then
        coVerify(exactly = 1) { historyLocalDataSource.addSearchQuery(title, SearchType.Query) }
    }

    @Test
    fun `clearSearchHistory should call clearSearchQueryByQuery with correct query`() = runTest {
        // Given
        val query = "drama"
        coEvery { historyLocalDataSource.clearSearchByQuery(query, SearchType.Query) } just Runs

        // When
        repository.clearSearchHistory(query, com.paris_2.domain.media.entity.SearchType.Query)

        // Then
        coVerify(exactly = 1) { historyLocalDataSource.clearSearchByQuery(query, SearchType.Query) }
    }

    @Test
    fun `clearAllSearchHistory should call clearAll`() = runTest {
        // Given
        coEvery { historyLocalDataSource.clearSearchQueries() } just Runs

        // When
        repository.clearAllSearchHistory()

        // Then
        coVerify(exactly = 1) { historyLocalDataSource.clearSearchQueries() }
    }
}
