package com.domain.search.repository

interface CountryRepository {
    suspend fun getAllCountries(): List<String>
}