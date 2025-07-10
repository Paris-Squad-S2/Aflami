package com.repository.search.repository

import com.repository.search.dataSource.local.HistoryLocalDataSource
import com.repository.search.entity.SearchHistoryEntity
import com.repository.search.mapper.toSearchHistories
import io.mockk.*
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
        val localHistory = listOf(SearchHistoryEntity("action"))
        coEvery { historyLocalDataSource.getAllSearchQueries() } returns localHistory

        // When
        val result = repository.getAllSearchHistory()

        // Then
        assertEquals(localHistory.toSearchHistories(), result)
    }

    @Test
    fun `addSearchHistory should call addSearchQuery with correct title`() = runTest {
        // Given
        val title = "comedy"
        coEvery { historyLocalDataSource.addSearchQuery(title) } just Runs

        // When
        repository.addSearchHistory(title)

        // Then
        coVerify(exactly = 1) { historyLocalDataSource.addSearchQuery(title) }
    }
}
