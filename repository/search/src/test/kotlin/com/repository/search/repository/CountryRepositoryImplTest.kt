package com.repository.search.repository

import org.junit.jupiter.api.Assertions.*
import com.domain.search.model.Country
import com.repository.search.dataSource.local.CountriesLocalDataSource
import com.repository.search.entity.CountryEntity
import com.repository.search.mapper.toCountry
import io.mockk.*
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CountryRepositoryImplTest {

    private lateinit var repository: CountryRepositoryImpl
    private val countriesLocalDataSource = mockk<CountriesLocalDataSource>()

    @BeforeEach
    fun setUp() {
        repository = CountryRepositoryImpl(countriesLocalDataSource)
    }

    @Test
    fun `getAllCountries should return local countries if not empty`() = runTest {
        // Given
        val localCountries = listOf(CountryEntity("1", "Egypt"))
        coEvery { countriesLocalDataSource.getCountries() } returns localCountries

        // When
        val result = repository.getAllCountries()

        // Then
        assertEquals(localCountries.toCountry(), result)
        coVerify(exactly = 0) { countriesLocalDataSource.addCountries() }
    }
}