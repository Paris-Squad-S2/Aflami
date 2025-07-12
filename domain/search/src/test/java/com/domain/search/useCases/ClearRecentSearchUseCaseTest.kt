package com.domain.search.useCases

import com.domain.search.model.SearchType
import com.domain.search.repository.SearchHistoryRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class ClearRecentSearchUseCaseTest{
  private lateinit var searchHistoryRepository: SearchHistoryRepository
  private lateinit var clearRecentSearchUseCase: ClearRecentSearchUseCase

  @BeforeEach
  fun setUp() {
   searchHistoryRepository = mockk()
   clearRecentSearchUseCase = ClearRecentSearchUseCase(searchHistoryRepository)
  }

   @Test
   fun `should call repository to clear specific search history`() = runTest {
    // Given
    val searchHistoryId = "search-123"
    coEvery { searchHistoryRepository.clearSearchHistory(searchHistoryId, SearchType.Query) } returns Unit

    // When
    clearRecentSearchUseCase(searchHistoryId, SearchType.Query)

    // Then
    coVerify(exactly = 1) { searchHistoryRepository.clearSearchHistory(searchHistoryId, SearchType.Query) }
   }

   @Test
   fun `should handle empty search history ID`() = runTest {
    // Given
    val emptyId = ""
    coEvery { searchHistoryRepository.clearSearchHistory(emptyId, SearchType.Query) } returns Unit

    // When
    clearRecentSearchUseCase(emptyId, SearchType.Query)

    // Then
    coVerify(exactly = 1) { searchHistoryRepository.clearSearchHistory(emptyId, SearchType.Query) }
   }

   @Test
   fun `should handle whitespace-only search history ID`() = runTest {
    // Given
    val whitespaceId = "   "
    coEvery { searchHistoryRepository.clearSearchHistory(whitespaceId, SearchType.Query) } returns Unit

    // When
    clearRecentSearchUseCase(whitespaceId, SearchType.Query)

    // Then
    coVerify(exactly = 1) { searchHistoryRepository.clearSearchHistory(whitespaceId, SearchType.Query) }
   }

   @Test
   fun `should handle special characters in search history ID`() = runTest {
    // Given
    val specialId = "search-123!@#$%^&*()"
    coEvery { searchHistoryRepository.clearSearchHistory(specialId, SearchType.Query) } returns Unit

    // When
    clearRecentSearchUseCase(specialId, SearchType.Query)

    // Then
    coVerify(exactly = 1) { searchHistoryRepository.clearSearchHistory(specialId, SearchType.Query) }
   }
 }