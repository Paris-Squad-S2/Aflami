package com.domain.search.useCases

import com.domain.search.repository.CountryRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import testUtils.sampleCountries

class AutoCompleteCountryUseCaseTest {
    private lateinit var countryRepository: CountryRepository
    private lateinit var autoCompleteCountryUseCase: AutoCompleteCountryUseCase

    @BeforeEach
    fun setUp() {
        countryRepository = mockk()
        autoCompleteCountryUseCase = AutoCompleteCountryUseCase(countryRepository)
    }

    @Test
    fun `should return 2 countries when query is 'united'`() = runTest {
        // Given
        coEvery { countryRepository.getAllCountries() } returns sampleCountries

        // When
        val result = autoCompleteCountryUseCase("united")

        // Then
        assertThat(result).hasSize(2)
    }

    @Test
    fun `should return correct countries when query is 'united'`() = runTest {
        // Given
        coEvery { countryRepository.getAllCountries() } returns sampleCountries

        // When
        val result = autoCompleteCountryUseCase("united")

        // Then
        assertThat(result.map { it.englishName }).containsExactly(
            "United States",
            "United Kingdom"
        )
    }

    @Test
    @DisplayName("Should return 1 country when query is 'CANADA'")
    fun `should return 1 country when query is uppercase`() = runTest {
        // Given
        coEvery { countryRepository.getAllCountries() } returns sampleCountries

        // When
        val result = autoCompleteCountryUseCase("CANADA")

        // Then
        assertThat(result).hasSize(1)
    }

    @Test
    fun `should return Canada as result when query is 'CANADA'`() = runTest {
        // Given
        coEvery { countryRepository.getAllCountries() } returns sampleCountries

        // When
        val result = autoCompleteCountryUseCase("CANADA")

        // Then
        assertThat(result.first().englishName).isEqualTo("Canada")
    }

    @Test
    fun `should return 4 countries when query is 'an'`() = runTest {
        // Given
        coEvery { countryRepository.getAllCountries() } returns sampleCountries

        // When
        val result = autoCompleteCountryUseCase("an")

        // Then
        assertThat(result).hasSize(4)
    }

    @Test
    fun `should return correct country names when query is 'an'`() = runTest {
        // Given
        coEvery { countryRepository.getAllCountries() } returns sampleCountries

        // When
        val result = autoCompleteCountryUseCase("an")

        // Then
        assertThat(result.map { it.englishName }).containsExactly(
            "Canada",
            "Germany",
            "France",
            "Japan"
        )
    }

    @Nested
    inner class QueryFilteringTests {
        @Test
        fun `should return empty list when no countries match query`() = runTest {
            // Given
            coEvery { countryRepository.getAllCountries() } returns sampleCountries

            // When
            val result = autoCompleteCountryUseCase("xyz")

            // Then
            assertThat(result).isEmpty()
        }

        @Test
        fun `should return all countries when query is empty`() = runTest {
            // Given
            coEvery { countryRepository.getAllCountries() } returns sampleCountries

            // When
            val result = autoCompleteCountryUseCase("")

            // Then
            assertThat(result).hasSize(sampleCountries.size)
        }

        @Test
        fun `should return all country objects when query is empty`() = runTest {
            // Given
            coEvery { countryRepository.getAllCountries() } returns sampleCountries

            // When
            val result = autoCompleteCountryUseCase("")

            // Then
            assertThat(result).containsExactlyElementsIn(sampleCountries)
        }
    }

    @Test
    fun `should call getAllCountries when querying`() = runTest {
        coEvery { countryRepository.getAllCountries() } returns sampleCountries

        autoCompleteCountryUseCase("any")

        coVerify(exactly = 1) { countryRepository.getAllCountries() }
    }
}