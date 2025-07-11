package com.datasource.local.search.datasource

import com.datasource.local.search.dao.CountryDao
import com.repository.search.entity.CountryEntity
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
            name = "palestine"
        )
    }

    @Test
    fun `getCountries should return countries when getAll in CountryDao called successfully`() =
        runTest {
            coEvery { countryDao.getAllCountries() } returns listOf(sampleCountry)

            val result = countriesLocalDataSource.getCountries()

            Assertions.assertEquals(sampleCountry.countryCode, result[0].countryCode)
        }

    @Test
    fun `addCountries should add countries when add in CountryDao called successfully`() = runTest {
        coEvery { countryDao.addCountries(any()) } returns Unit

        countriesLocalDataSource.addCountries()

        coVerify { countryDao.addCountries(any()) }

    }

}