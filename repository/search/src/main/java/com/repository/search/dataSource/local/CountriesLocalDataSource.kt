package com.repository.search.dataSource.local

import com.repository.search.entity.CountryEntity

interface CountriesLocalDataSource {
    suspend fun addCountries()
    suspend fun getCountries(): List<CountryEntity>
}