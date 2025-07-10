package com.domain.search.repository

interface CountryRepository {
    suspend fun getAllCountries(): List<String>
}

class CountryRepositoryFakeImpl : CountryRepository {
    override suspend fun getAllCountries(): List<String> {
        return listOf(
            "United States",
            "Canada",
            "United Kingdom",
            "Australia",
            "India",
            "Japan",
            "South Korea",
            "France",
            "Germany",
            "Italy",
            "Spain",
            "Spain",
            "Spain",
            "Spain",
            "Spain",
            "Spain",
            "Spain",
            "Brazil",
            "Mexico",
            "Russia",
            "China"
        )
    }
}