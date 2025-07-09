package com.repository.search.repository

import com.domain.search.repository.CountryRepository
import com.repository.search.dataSource.CountriesLocalDataSource
import com.repository.search.entity.CountryEntity

class CountryRepositoryImpl(
    private val isNetworkConnected: Boolean,
    private val countriesLocalDataSource: CountriesLocalDataSource,
    // private val countriesRemoteDataSource: ContriesRemoteDataSource,
): CountryRepository {
    override suspend fun getAllCountries(): List<String> {
        return if (isNetworkConnected)
            TODO()
        else
            countriesLocalDataSource.getCountries().toCountriesName()
    }
}

fun List<CountryEntity>.toCountriesName(): List<String> = this.map { it.name }