package com.domain.search.useCases

import com.domain.search.model.Country
import com.domain.search.repository.CountryRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertNull
import kotlin.test.Test
import kotlin.test.assertEquals

class GetCountryCodeByNameUseCaseTest {

    private lateinit var countryRepository: CountryRepository
    private lateinit var useCase: GetCountryCodeByNameUseCase

    @BeforeEach
    fun setUp() {
        countryRepository = mockk()
        useCase = GetCountryCodeByNameUseCase(countryRepository)
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
        val result = useCase("France")

        //Then
        assertEquals("FR", result)
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
        val result = useCase("france")

        //Then
        assertEquals("FR", result)
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
        val result = useCase("Canada")

        //Then
        assertNull(result)
    }

    @Test
    fun `invoke should return null when country list is empty`() = runTest {

        //Given
        coEvery { countryRepository.getAllCountries() } returns emptyList()

        //When
        val result = useCase("France")

        //Then
        assertNull(result)

    }
}