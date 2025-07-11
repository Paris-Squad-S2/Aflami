package com.domain.search.useCases

import com.domain.search.model.Country
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

class AutoCompleteCountryUseCaseTest {
    private lateinit var countryRepository: CountryRepository
    private lateinit var autoCompleteCountryUseCase: AutoCompleteCountryUseCase

    @BeforeEach
    fun setUp() {
        countryRepository = mockk()
        autoCompleteCountryUseCase = AutoCompleteCountryUseCase(countryRepository)
    }

    @Test
    fun `should return countries matching query case`() = runTest {
        // Given
        coEvery { countryRepository.getAllCountries() } returns sampleCountries

        // When
        val result = autoCompleteCountryUseCase("united")

        // Then
        assertThat(result).hasSize(2)
        assertThat(result.map { it.countryName }).containsExactly(
            "United States",
            "United Kingdom"
        )
        coVerify(exactly = 1) { countryRepository.getAllCountries() }
    }

    @Test
    @DisplayName("Should return countries matching query with different case")
    fun `should return countries matching query with different case`() = runTest {
        // Given
        coEvery { countryRepository.getAllCountries() } returns sampleCountries

        // When
        val result = autoCompleteCountryUseCase("CANADA")

        // Then
        assertThat(result).hasSize(1)
        assertThat(result.first().countryName).isEqualTo("Canada")
        coVerify(exactly = 1) { countryRepository.getAllCountries() }
    }

    @Test
    fun `should return countries matching partial query`() = runTest {
        // Given
        coEvery { countryRepository.getAllCountries() } returns sampleCountries

        // When
        val result = autoCompleteCountryUseCase("an")

        // Then
        assertThat(result).hasSize(4)
        assertThat(result.map { it.countryName }).containsExactly(
            "Canada",
            "Germany",
            "France",
            "Japan"
        )
        coVerify(exactly = 1) { countryRepository.getAllCountries() }
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
            coVerify(exactly = 1) { countryRepository.getAllCountries() }
        }

        @Test
        fun `should return all countries when query is empty`() = runTest {
            // Given
            coEvery { countryRepository.getAllCountries() } returns sampleCountries

            // When
            val result = autoCompleteCountryUseCase("")

            // Then
            assertThat(result).hasSize(sampleCountries.size)
            assertThat(result).containsExactlyElementsIn(sampleCountries)
            coVerify(exactly = 1) { countryRepository.getAllCountries() }
        }
    }

    private val sampleCountries = listOf(
        Country(countryCode = "1612", countryName = "United States"),
        Country(countryCode = "2113", countryName = "United Kingdom"),
        Country(countryCode = "1213", countryName = "Canada"),
        Country(countryCode = "3478", countryName = "Australia"),
        Country(countryCode = "3462", countryName = "Germany"),
        Country(countryCode = "1237", countryName = "France"),
        Country(countryCode = "9872", countryName = "Japan"),
        Country(countryCode = "2434", countryName = "Brazil"),
        Country(countryCode = "2312", countryName = "India"),
        Country(countryCode = "7484", countryName = "China")
    )
}