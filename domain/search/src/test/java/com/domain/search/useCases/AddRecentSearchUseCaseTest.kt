package com.domain.search.useCases

import com.domain.search.repository.SearchHistoryRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class AddRecentSearchUseCaseTest{
    private lateinit var searchHistoryRepository: SearchHistoryRepository
    private lateinit var addRecentSearchUseCase: AddRecentSearchUseCase

    @BeforeEach
    fun setup() {
        searchHistoryRepository = mockk()
        addRecentSearchUseCase = AddRecentSearchUseCase(searchHistoryRepository)
    }

    @ParameterizedTest
    @ValueSource(strings = [
        "Kotlin",
        "Android Architecture Components",
        "Clean Architecture",
        "Jetpack Compose",
        "Room Database",
        "Coroutines and Flow"
    ])
    fun `should add search history when valid search title is provided`(searchTitle: String) = runTest {
        // Given
        coEvery { searchHistoryRepository.addSearchHistory(any()) } returns Unit

        // When
        val result = addRecentSearchUseCase(searchTitle)

        // Then
        assertThat(result).isEqualTo(Unit)
        coVerify(exactly = 1) { searchHistoryRepository.addSearchHistory(searchTitle) }
    }

    @ParameterizedTest
    @ValueSource(strings = [
        "Search with @#\$%^&*()",
        "Search with 123 numbers",
        "Search;with;semicolons",
        "Search\"with\"quotes",
        "Search,with,commas",
        "Search?with?questions"
    ])
    fun `should handle special characters in search title`(searchTitle: String) = runTest {
        // Given
        coEvery { searchHistoryRepository.addSearchHistory(any()) } returns Unit

        // When
        addRecentSearchUseCase(searchTitle)

        // Then
        coVerify(exactly = 1) { searchHistoryRepository.addSearchHistory(searchTitle) }
    }

    @ParameterizedTest
    @ValueSource(strings = [""," ", "  ", "\t", "\n", "   \t  \n  "])
    fun `Should not add search history for blank search titles`(searchTitle: String) = runTest {
        // Given
        coEvery { searchHistoryRepository.addSearchHistory(any()) } returns Unit

        // When
        addRecentSearchUseCase(searchTitle)

        // Then
        coVerify(exactly = 0) { searchHistoryRepository.addSearchHistory(searchTitle) }
    }
}