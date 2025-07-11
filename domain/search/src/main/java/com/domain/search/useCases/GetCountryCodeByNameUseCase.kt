package com.domain.search.useCases

import com.domain.search.repository.CountryRepository

class GetCountryCodeByNameUseCase(
    private val countryRepository: CountryRepository,
) {
    suspend operator fun invoke(countryName: String): String? {
        val allCountries = countryRepository.getAllCountries()
        if (allCountries.any { it.countryCode.equals(countryName, ignoreCase = true) }) {
            return countryName
        }
        return allCountries
            .firstOrNull { it.countryName.contains(countryName, ignoreCase = true) }
            ?.countryCode
    }
}