package com.domain.search.useCases

import com.domain.search.model.Country
import com.domain.search.repository.CountryRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertNull
import kotlin.test.Test
import kotlin.test.assertEquals

class GetCountryCodeByNameUseCaseTest {

    private lateinit var countryRepository: CountryRepository
    private lateinit var getCountryCodeByNameUseCase: GetCountryCodeByNameUseCase

    @BeforeEach
    fun setUp() {
        countryRepository = mockk()
        getCountryCodeByNameUseCase = GetCountryCodeByNameUseCase(countryRepository)
    }

    @Test
    fun `invoke should return country code when country name exists`() = runTest {
        //Given
        val countries = listOf(
            Country("EG", "Egypt"),
            Country("FR", "France"),
        )

        coEvery { countryRepository.getAllCountries() } returns countries

        //When
        val result = getCountryCodeByNameUseCase("France")

        //Then
        assertEquals("FR", result)
        coVerify(exactly = 1) { countryRepository.getAllCountries() }
    }

    @Test
    fun `invoke should return country code`() = runTest {

        //Given
        val countries = listOf(
            Country("EG", "Egypt"),
            Country("FR", "France")
        )
        coEvery { countryRepository.getAllCountries() } returns countries

        // When
        val result = getCountryCodeByNameUseCase("france")

        //Then
        assertEquals("FR", result)
        coVerify(exactly = 1) { countryRepository.getAllCountries() }
    }

    @Test
    fun `invoke should return null when country name is not found`() = runTest {

        //Given
        val countries = listOf(
            Country("EG", "Egypt"),
            Country("FR", "France")
        )

        coEvery { countryRepository.getAllCountries() } returns countries

        //When
        val result = getCountryCodeByNameUseCase("Canada")

        //Then
        assertNull(result)
        coVerify(exactly = 1) { countryRepository.getAllCountries() }
    }

    @Test
    fun `invoke should return null when country list is empty`() = runTest {

        //Given
        coEvery { countryRepository.getAllCountries() } returns emptyList()

        //When
        val result = getCountryCodeByNameUseCase("France")

        //Then
        assertNull(result)
        coVerify(exactly = 1) { countryRepository.getAllCountries() }
    }
}