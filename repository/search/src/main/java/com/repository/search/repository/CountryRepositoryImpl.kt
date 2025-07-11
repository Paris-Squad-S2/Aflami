package com.repository.search.repository

import com.domain.search.model.Country
import com.domain.search.repository.CountryRepository
import com.repository.search.dataSource.local.CountriesLocalDataSource
import com.repository.search.mapper.toCountry

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
