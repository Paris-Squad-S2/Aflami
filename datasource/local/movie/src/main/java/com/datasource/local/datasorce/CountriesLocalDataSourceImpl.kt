package com.datasource.local.datasorce

import com.datasource.local.dao.CountryDao
import com.repository.datasorce.local.CountriesLocalDataSource
import com.repository.entity.CountryEntity
import com.repository.util.Util


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