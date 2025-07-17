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
    fun `should return correct country code when name matches`() = runTest {

        // Given
        coEvery { countryRepository.getAllCountries() } returns countries

        // When
        val result = getCountryCodeByNameUseCase("France")

        // Then
        assertEquals("FR", result)
    }

    @Test
    fun `should verify repository is exactly once when name matches`() = runTest {

        // Given
        coEvery { countryRepository.getAllCountries() } returns countries

        // When
        getCountryCodeByNameUseCase("France")

        // Then
        coVerify(exactly = 1) { countryRepository.getAllCountries() }
    }

    @Test
    fun `should return country code even if name case is different`() = runTest {

        // Given
        coEvery { countryRepository.getAllCountries() } returns countries

        // When
        val result = getCountryCodeByNameUseCase("france")

        // Then
        assertEquals("FR", result)
    }

    @Test
    fun `should return null when country name is not found`() = runTest {

        //Given
        coEvery { countryRepository.getAllCountries() } returns countries

        //When
        val result = getCountryCodeByNameUseCase("Canada")

        //Then
        assertNull(result)
    }

    @Test
    fun `should verify repository is called when name not found`() = runTest {

        // Given
        coEvery { countryRepository.getAllCountries() } returns countries

        // When
        getCountryCodeByNameUseCase("Canada")

        // Then
        coVerify(exactly = 1) { countryRepository.getAllCountries() }
    }

    @Test
    fun `should return null when country list is empty`() = runTest {

        //Given
        coEvery { countryRepository.getAllCountries() } returns emptyList()

        //When
        val result = getCountryCodeByNameUseCase("France")

        //Then
        assertNull(result)
    }

    @Test
    fun `should verify repository is called when list is empty`() = runTest {
        // Given
        coEvery { countryRepository.getAllCountries() } returns emptyList()

        // When
        getCountryCodeByNameUseCase("France")

        // Then
        coVerify(exactly = 1) { countryRepository.getAllCountries() }
    }

    @Test
    fun `should return uppercased input when input matches country code`() = runTest {

        // Given
        coEvery { countryRepository.getAllCountries() } returns countries

        // When
        val result = getCountryCodeByNameUseCase("eg")

        // Then
        assertEquals("EG", result)
    }

    @Test
    fun `should return country code when input is contained in country name`() = runTest {
        // Given
        coEvery { countryRepository.getAllCountries() } returns countries

        // When
        val result = getCountryCodeByNameUseCase("fra")

        // Then
        assertEquals("FR", result)
    }

    @Test
    fun `should verify repository is called once when input is country code`() = runTest {
        // Given
        val countries = listOf(Country("EG", "Egypt" , "مصر" ))
        coEvery { countryRepository.getAllCountries() } returns countries

        // When
        getCountryCodeByNameUseCase("eg")

        // Then
        coVerify(exactly = 1) { countryRepository.getAllCountries() }
    }

    companion object{
        val countries = listOf(
            Country("EG", "Egypt" , "مصر"),
            Country("FR", "France", "فرنسا")
        )
    }

}