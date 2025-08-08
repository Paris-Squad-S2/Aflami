package com.repository.media.datasource.local

import com.repository.media.entity.CountryEntity


interface CountriesLocalDataSource {
    suspend fun addCountries()
    suspend fun getCountries(): List<CountryEntity>
}