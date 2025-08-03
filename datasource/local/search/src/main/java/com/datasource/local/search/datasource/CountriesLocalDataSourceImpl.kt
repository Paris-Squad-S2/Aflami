package com.datasource.local.search.datasource

import com.datasource.local.search.dao.CountryDao
import com.repository.media.datasource.local.CountriesLocalDataSource
import com.repository.media.entity.CountryEntity
import com.repository.media.util.Util

class CountriesLocalDataSourceImpl(
    private val dao: CountryDao
) : CountriesLocalDataSource {
    override suspend fun addCountries() {
        dao.addCountries(Util.countryList)
    }

    override suspend fun getCountries(): List<CountryEntity> {
        return dao.getAllCountries()
    }
}