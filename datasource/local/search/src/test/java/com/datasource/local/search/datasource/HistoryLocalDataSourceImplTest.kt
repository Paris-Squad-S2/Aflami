package com.datasource.local.search.datasource

import com.datasource.local.search.dao.SearchHistoryDao
import com.repository.search.entity.SearchHistoryEntity
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class HistoryLocalDataSourceImplTest {
    private lateinit var historyLocalDataSource: HistoryLocalDataSourceImpl
    private val searchHistoryDao: SearchHistoryDao = mockk(relaxed = true)
    private lateinit var sampleSearchHistory: SearchHistoryEntity

    @BeforeEach
    fun setUp() {
        historyLocalDataSource = HistoryLocalDataSourceImpl(searchHistoryDao)
        sampleSearchHistory = SearchHistoryEntity(
            searchQuery = "a"
        )
    }

    @Test
    fun `getAllSearchQueries should return genres when getAll in SearchHistoryDao called successfully`() =
        runTest {
            coEvery { searchHistoryDao.getAllSearchQueries() } returns listOf(sampleSearchHistory)

            val result = historyLocalDataSource.getAllSearchQueries()

            assertEquals(sampleSearchHistory.searchQuery, result[0].searchQuery)
        }

    @Test
    fun `addSearchQuery should add SearchQuery when add in SearchHistoryDao called successfully`() =
        runTest {
            coEvery { searchHistoryDao.addSearchQuery(any()) } returns Unit

            historyLocalDataSource.addSearchQuery("aaa")

            coVerify { searchHistoryDao.addSearchQuery(any()) }
        }

    @Test
    fun `clearSearchQueryByQuery should clear SearchQuery when clear in SearchHistoryDao called successfully`() =
        runTest {
            coEvery { searchHistoryDao.clearSearchQueryByQuery(any()) } returns Unit

            historyLocalDataSource.clearSearchQueryByQuery("aaa")

            coVerify { searchHistoryDao.clearSearchQueryByQuery(any()) }
        }

    @Test
    fun `clearAllSearchQueries should clear All SearchQueries when clear in SearchHistoryDao called successfully`() =
        runTest {
            coEvery { searchHistoryDao.clearAllSearchQueries() } returns Unit

            historyLocalDataSource.clearAll()

            coVerify { searchHistoryDao.clearAllSearchQueries() }

        }

}