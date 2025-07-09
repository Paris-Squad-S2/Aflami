package com.domain.search.useCases

import com.domain.search.repository.CountryRepository

class AutoCompleteCountry(
    private val countryRepository: CountryRepository,
) {
    suspend operator fun invoke(query: String): List<String> {
        return countryRepository.getAllCountries().filter { it.contains(query, ignoreCase = true) }
    }
}