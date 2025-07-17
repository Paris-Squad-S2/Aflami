package com.datasource.local.search.datasource

import com.datasource.local.search.dao.CountryDao
import com.google.common.truth.Truth.assertThat
import com.repository.search.entity.CountryEntity
import com.repository.search.util.Util
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CountriesLocalDataSourceImplTest {
    private lateinit var countriesLocalDataSource: CountriesLocalDataSourceImpl
    private val countryDao: CountryDao = mockk(relaxed = false)
    private lateinit var sampleCountry: CountryEntity

    @BeforeEach
    fun setUp() {
        countriesLocalDataSource = CountriesLocalDataSourceImpl(countryDao)
        sampleCountry = CountryEntity(
            countryCode = "ps",
            englishName = "palestine",
            arabicName = "فلسطين"
        )
    }

    @Test
    fun `getCountries should return countries when getAll in CountryDao called successfully`() =
        runTest {
            // Given
            coEvery { countryDao.getAllCountries() } returns listOf(sampleCountry)
            // When
            val result = countriesLocalDataSource.getCountries()
            // Then
            assertThat(result).containsExactly(sampleCountry)
        }

    @Test
    fun `getCountries should return empty list when CountryDao returns nothing`() =
        runTest {
            // Given
            coEvery { countryDao.getAllCountries() } returns emptyList()
            // When
            val result = countriesLocalDataSource.getCountries()
            // Then
            Assertions.assertTrue(result.isEmpty())
        }

    @Test
    fun `addCountries should add countries when add in CountryDao called successfully`() =
        runTest {
            // Given
            coEvery { countryDao.addCountries(any()) } returns Unit
            // When
            countriesLocalDataSource.addCountries()
            // Then
            coVerify { countryDao.addCountries(any()) }
        }

    @Test
    fun `addCountries should add countries from Util countryList`() =
        runTest {
            // Given
            coEvery { countryDao.addCountries(any()) } returns Unit
            // When
            countriesLocalDataSource.addCountries()
            // Then
            coVerify {
                countryDao.addCountries(withArg {
                    Assertions.assertEquals(Util.countryList, it)
                })
            }
        }
}