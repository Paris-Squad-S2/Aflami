package com.repository.media.repository

import com.paris.domain.media.entity.Country
import com.paris.domain.media.repository.CountryRepository
import com.repository.media.datasource.local.CountriesLocalDataSource
import com.repository.media.mapper.search.toCountry

class CountryRepositoryImpl(
    private val countriesLocalDataSource: CountriesLocalDataSource,
) : CountryRepository {

    override suspend fun getAllCountries(): List<Country> {
        val countries = countriesLocalDataSource.getCountries().toCountry()
        if (countries.isNotEmpty()) return countries
        countriesLocalDataSource.addCountries()
        return countriesLocalDataSource.getCountries().toCountry()
    }
}
