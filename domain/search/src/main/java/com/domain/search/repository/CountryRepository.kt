package com.domain.search.repository

import com.domain.search.model.Country

interface CountryRepository {
    suspend fun getAllCountries(): List<Country>
}

class CountryRepositoryFakeImpl : CountryRepository {
    override suspend fun getAllCountries(): List<Country> {
        return listOf(
            Country("US", "United States"),
            Country("CA", "Canada"),
            Country("GB", "United Kingdom"),
            Country("AU", "Australia"),
            Country("IN", "India"),
            Country("JP", "Japan"),
            Country("KR", "South Korea"),
            Country("FR", "France"),
            Country("DE", "Germany"),
            Country("IT", "Italy"),
            Country("ES", "Spain"),
            Country("BR", "Brazil"),
            Country("MX", "Mexico"),
            Country("RU", "Russia"),
            Country("CN", "China")
        )
    }
}