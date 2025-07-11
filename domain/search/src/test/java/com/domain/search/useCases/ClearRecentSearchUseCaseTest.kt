package com.domain.search.useCases

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
    coEvery { searchHistoryRepository.clearSearchHistory(searchHistoryId) } returns Unit

    // When
    clearRecentSearchUseCase(searchHistoryId)

    // Then
    coVerify(exactly = 1) { searchHistoryRepository.clearSearchHistory(searchHistoryId) }
   }

   @Test
   fun `should handle empty search history ID`() = runTest {
    // Given
    val emptyId = ""
    coEvery { searchHistoryRepository.clearSearchHistory(emptyId) } returns Unit

    // When
    clearRecentSearchUseCase(emptyId)

    // Then
    coVerify(exactly = 1) { searchHistoryRepository.clearSearchHistory(emptyId) }
   }

   @Test
   fun `should handle whitespace-only search history ID`() = runTest {
    // Given
    val whitespaceId = "   "
    coEvery { searchHistoryRepository.clearSearchHistory(whitespaceId) } returns Unit

    // When
    clearRecentSearchUseCase(whitespaceId)

    // Then
    coVerify(exactly = 1) { searchHistoryRepository.clearSearchHistory(whitespaceId) }
   }

   @Test
   fun `should handle special characters in search history ID`() = runTest {
    // Given
    val specialId = "search-123!@#$%^&*()"
    coEvery { searchHistoryRepository.clearSearchHistory(specialId) } returns Unit

    // When
    clearRecentSearchUseCase(specialId)

    // Then
    coVerify(exactly = 1) { searchHistoryRepository.clearSearchHistory(specialId) }
   }
 }