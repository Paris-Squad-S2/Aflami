package com.repository.datasorce.local

import com.repository.entity.CountryEntity


interface CountriesLocalDataSource {
    suspend fun addCountries()
    suspend fun getCountries(): List<CountryEntity>
}