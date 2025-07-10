package com.domain.search.useCases

import com.domain.search.model.Country
import com.domain.search.repository.CountryRepository

class AutoCompleteCountryUseCase(
    private val countryRepository: CountryRepository,
) {
    suspend operator fun invoke(query: String): List<Country> {
        return countryRepository.getAllCountries().filter { it.countryName.contains(query, ignoreCase = true) }
    }
}