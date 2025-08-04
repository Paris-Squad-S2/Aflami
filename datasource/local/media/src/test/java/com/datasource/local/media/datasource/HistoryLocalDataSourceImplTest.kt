package com.datasource.local.media.datasource

import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.datasource.local.media.dao.SearchHistoryDao
import com.google.common.truth.Truth.assertThat
import com.repository.media.entity.SearchHistoryEntity
import com.repository.media.entity.SearchType
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class HistoryLocalDataSourceImplTest {
    private lateinit var historyLocalDataSource: HistoryLocalDataSourceImpl
    private val searchHistoryDao: SearchHistoryDao = mockk(relaxed = true)
    private val workManager: WorkManager = mockk(relaxed = true)

    @BeforeEach
    fun setUp() {
        historyLocalDataSource = HistoryLocalDataSourceImpl(searchHistoryDao, workManager)
    }

    @Test
    fun `getAllSearchQueries should return genres when getAll in SearchHistoryDao called successfully`() =
        runTest {
            // Given
            coEvery { searchHistoryDao.getAllSearchQueries() } returns flowOf(
                listOf(
                    sampleSearchHistory
                )
            )
            // When
            val result = historyLocalDataSource.getAllSearchQueries().first()
            // Then
            assertThat(result).containsExactly(sampleSearchHistory)
        }

    @Test
    fun `addSearchQuery should call DAO method once`() = runTest {
        //Given
        coEvery { searchHistoryDao.addSearchQuery(any()) } returns Unit

        //When
        historyLocalDataSource.addSearchQuery("aaa", SearchType.Query)

        //Then
        coVerify(exactly = 1) { searchHistoryDao.addSearchQuery(any()) }
    }

    @Test
    fun `addSearchQuery should enqueue work request`() = runTest {
        //Given
        historyLocalDataSource.addSearchQuery("aaa", SearchType.Query)

        //When&Then
        verify(exactly = 1) { workManager.enqueue(any<OneTimeWorkRequest>()) }
    }

    @Test
    fun `clearSearchQueryByQuery should clear SearchQuery when clear in SearchHistoryDao called successfully`() =
        runTest {
            // Given
            coEvery {
                searchHistoryDao.clearSearchQueryByQuery(
                    "aaa",
                    SearchType.Query
                )
            } returns Unit
            // When
            historyLocalDataSource.clearSearchQueryByQuery("aaa", SearchType.Query)
            // Then
            coVerify { searchHistoryDao.clearSearchQueryByQuery("aaa", SearchType.Query) }
        }

    @Test
    fun `clearAllSearchQueries should clear All SearchQueries when clear in SearchHistoryDao called successfully`() =
        runTest {
            // Given
            coEvery { searchHistoryDao.clearAllSearchQueries() } returns Unit
            // When
            historyLocalDataSource.clearAll()
            // Then
            coVerify { searchHistoryDao.clearAllSearchQueries() }
        }

    @Test
    fun `getSearchHistoryQuery should return entity when DAO returns non-null`() =
        runTest {
            // Given
            coEvery {
                searchHistoryDao.getSearchHistoryQuery(
                    "a",
                    SearchType.Query
                )
            } returns sampleSearchHistory
            // When
            val result = historyLocalDataSource.getSearchHistoryQuery("a", SearchType.Query)
            // Then
            assertThat(result).isEqualTo(sampleSearchHistory)
        }

    @Test
    fun `getSearchHistoryQuery should call DAO once when non-null result returned`() = runTest {
        //Given
        coEvery {
            searchHistoryDao.getSearchHistoryQuery("a", SearchType.Query)
        } returns sampleSearchHistory

        //When
        historyLocalDataSource.getSearchHistoryQuery("a", SearchType.Query)

        //Then
        coVerify(exactly = 1) { searchHistoryDao.getSearchHistoryQuery("a", SearchType.Query) }
    }

    @Test
    fun `getSearchHistoryQuery should return null when DAO returns null`() =
        runTest {
            // Given
            coEvery { searchHistoryDao.getSearchHistoryQuery("b", SearchType.Query) } returns null
            // When
            val result = historyLocalDataSource.getSearchHistoryQuery("b", SearchType.Query)
            // Then
            assertThat(result).isNull()
        }

    @Test
    fun `getSearchHistoryQuery should call DAO once when result is null`() = runTest {
        //Given
        coEvery {
            searchHistoryDao.getSearchHistoryQuery("b", SearchType.Query)
        } returns null

        //When
        historyLocalDataSource.getSearchHistoryQuery("b", SearchType.Query)

        //Then
        coVerify(exactly = 1) { searchHistoryDao.getSearchHistoryQuery("b", SearchType.Query) }
    }

    private companion object {
        val sampleSearchHistory = SearchHistoryEntity(
            searchQuery = "a",
            searchType = SearchType.Query
        )
    }
}