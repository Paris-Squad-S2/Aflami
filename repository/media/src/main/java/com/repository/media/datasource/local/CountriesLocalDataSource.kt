package com.repository.media.datasource.local

import com.repository.media.models.local.media.CountryEntity


interface CountriesLocalDataSource {
    suspend fun addCountries()
    suspend fun getCountries(): List<CountryEntity>
}