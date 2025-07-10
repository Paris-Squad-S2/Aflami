package com.repository.search.repository

import com.domain.search.repository.CountryRepository
import com.example.search.GenresRemoteDataSource
import com.repository.search.NetworkConnectionChecker
import com.repository.search.dataSource.CountriesLocalDataSource
import com.repository.search.entity.CountryEntity
import com.repository.search.mapper.toCountriesName

class CountryRepositoryImpl(
    private val networkConnectionChecker: NetworkConnectionChecker,
    private val countriesLocalDataSource: CountriesLocalDataSource,
    private val genresRemoteDataSource: GenresRemoteDataSource
): CountryRepository {

    override suspend fun getAllCountries(): List<String> {
        return try {
            return if (networkConnectionChecker.isConnected.value)
                genresRemoteDataSource.getAllGenres().genres.toCountriesName()
            else
                countriesLocalDataSource.getCountries().toCountriesName()
        } catch (e: Exception) {
            throw e
        }
    }
}

fun List<CountryEntity>.toCountriesName(): List<String> = this.map { it.name }