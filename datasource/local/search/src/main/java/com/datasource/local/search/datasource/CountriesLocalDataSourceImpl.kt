package com.datasource.local.search.datasource

import com.datasource.local.search.dao.CountryDao
import com.repository.search.dataSource.local.CountriesLocalDataSource
import com.repository.search.entity.CountryEntity
import com.repository.search.util.Util
import javax.inject.Inject

class CountriesLocalDataSourceImpl @Inject constructor(
    private val dao: CountryDao
) : CountriesLocalDataSource {
    override suspend fun addCountries() {
        dao.addCountries(Util.countryList)
    }

    override suspend fun getCountries(): List<CountryEntity> {
        return dao.getAllCountries()
    }
}