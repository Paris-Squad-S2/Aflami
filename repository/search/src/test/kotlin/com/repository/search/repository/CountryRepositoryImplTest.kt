package com.repository.search.repository

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

    @Test
    fun `getAllCountries should call addCountries when local is empty`() = runTest {
        // Given
        val empty = emptyList<CountryEntity>()
        val populated = listOf(CountryEntity("2", "France"))

        coEvery { countriesLocalDataSource.getCountries() } returnsMany listOf(empty, populated)
        coEvery { countriesLocalDataSource.addCountries() } just Runs

        // When
        val result = repository.getAllCountries()

        // Then
        assertEquals(populated.toCountry(), result)
        coVerify(exactly = 1) { countriesLocalDataSource.addCountries() }
    }

    @Test
    fun `getAllCountries should return empty list if addCountries doesn't add anything`() = runTest {
        // Given
        val empty = emptyList<CountryEntity>()
        coEvery { countriesLocalDataSource.getCountries() } returns empty
        coEvery { countriesLocalDataSource.addCountries() } just Runs

        // When
        val result = repository.getAllCountries()

        // Then
        assertEquals(empty.toCountry(), result)
        coVerify(exactly = 1) { countriesLocalDataSource.addCountries() }
    }
}