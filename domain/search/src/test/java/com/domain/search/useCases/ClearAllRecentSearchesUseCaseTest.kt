package com.domain.search.useCases

import com.domain.search.repository.SearchHistoryRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ClearAllRecentSearchesUseCaseTest {
    private lateinit var searchHistoryRepository: SearchHistoryRepository
    private lateinit var clearAllRecentSearchesUseCase: ClearAllRecentSearchesUseCase

    @BeforeEach
    fun setUp() {
        searchHistoryRepository = mockk()
        clearAllRecentSearchesUseCase = ClearAllRecentSearchesUseCase(searchHistoryRepository)
    }

    @Test
    fun `should call repository to clear all search history`() = runTest {
        // Given
        coEvery { searchHistoryRepository.clearAllSearchHistory() } returns Unit

        // When
        clearAllRecentSearchesUseCase()

        // Then
        coVerify(exactly = 1) { searchHistoryRepository.clearAllSearchHistory() }
    }


    @Test
    fun `should throw exception when repository fails to clear search history`() = runTest {

        // Given
        val exception = RuntimeException("Failed to clear search history")
        coEvery { searchHistoryRepository.clearAllSearchHistory() } throws exception

        // When & Then
        try {
            clearAllRecentSearchesUseCase()
            assertThat(false).isTrue()
        } catch (e: RuntimeException) {
            assertThat(e).isInstanceOf(RuntimeException::class.java)
        }
    }


    @Test
    fun `should throw exception with correct message when repository fails`() = runTest {

        // Given
        val exception = RuntimeException("Failed to clear search history")
        coEvery { searchHistoryRepository.clearAllSearchHistory() } throws exception

        // When & Then
        try {
            clearAllRecentSearchesUseCase()
        } catch (e: RuntimeException) {
            assertThat(e.message).isEqualTo("Failed to clear search history")
        }
    }

    @Test
    fun `should verify repository is called when exception is thrown`() = runTest {

        // Given
        val exception = RuntimeException("Failed to clear search history")
        coEvery { searchHistoryRepository.clearAllSearchHistory() } throws exception

        // When
        try {
            clearAllRecentSearchesUseCase()
        } catch (_: Exception) {}

        // Then
        coVerify(exactly = 1) { searchHistoryRepository.clearAllSearchHistory() }
    }
    
}