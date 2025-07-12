package com.datasource.local.search.datasource

import com.datasource.local.search.dao.SearchHistoryDao
import com.repository.search.entity.SearchHistoryEntity
import com.repository.search.entity.SearchType
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNull

class HistoryLocalDataSourceImplTest {
    private lateinit var historyLocalDataSource: HistoryLocalDataSourceImpl
    private val searchHistoryDao: SearchHistoryDao = mockk(relaxed = true)
    private lateinit var sampleSearchHistory: SearchHistoryEntity

    @BeforeEach
    fun setUp() {
        historyLocalDataSource = HistoryLocalDataSourceImpl(searchHistoryDao)
        sampleSearchHistory = SearchHistoryEntity(
            searchQuery = "a",
            searchType = SearchType.Query
        )
    }

    @Test
    fun `getAllSearchQueries should return genres when getAll in SearchHistoryDao called successfully`() = runTest {
        coEvery { searchHistoryDao.getAllSearchQueries() } returns kotlinx.coroutines.flow.flowOf(listOf(sampleSearchHistory))

        val result = historyLocalDataSource.getAllSearchQueries().first()

        assertEquals(sampleSearchHistory.searchQuery, result[0].searchQuery)
    }

    @Test
    fun `addSearchQuery should add SearchQuery when add in SearchHistoryDao called successfully`() =
        runTest {
            coEvery { searchHistoryDao.addSearchQuery(any()) } returns Unit

            historyLocalDataSource.addSearchQuery("aaa", SearchType.Query)

            coVerify { searchHistoryDao.addSearchQuery(any()) }
        }

    @Test
    fun `clearSearchQueryByQuery should clear SearchQuery when clear in SearchHistoryDao called successfully`() =
        runTest {
            coEvery { searchHistoryDao.clearSearchQueryByQuery(any(), SearchType.Query) } returns Unit

            historyLocalDataSource.clearSearchQueryByQuery("aaa", SearchType.Query)

            coVerify { searchHistoryDao.clearSearchQueryByQuery(any(), SearchType.Query) }
        }

    @Test
    fun `clearAllSearchQueries should clear All SearchQueries when clear in SearchHistoryDao called successfully`() =
        runTest {
            coEvery { searchHistoryDao.clearAllSearchQueries() } returns Unit

            historyLocalDataSource.clearAll()

            coVerify { searchHistoryDao.clearAllSearchQueries() }

        }

    @Test
    fun `getSearchHistoryQuery should return entity when DAO returns non-null`() = runTest {
        coEvery { searchHistoryDao.getSearchHistoryQuery("a", SearchType.Query) } returns sampleSearchHistory

        val result = historyLocalDataSource.getSearchHistoryQuery("a", SearchType.Query)

        assertEquals(sampleSearchHistory, result)
        coVerify { searchHistoryDao.getSearchHistoryQuery("a", SearchType.Query) }
    }

    @Test
    fun `getSearchHistoryQuery should return null when DAO returns null`() = runTest {
        coEvery { searchHistoryDao.getSearchHistoryQuery("b", SearchType.Query) } returns null

        val result = historyLocalDataSource.getSearchHistoryQuery("b", SearchType.Query)

        assertNull(result)
        coVerify { searchHistoryDao.getSearchHistoryQuery("b", SearchType.Query) }
    }

}