package com.domain.search.useCases

import com.domain.search.repository.CountryRepository

class GetCountryCodeByNameUseCase(
    private val countryRepository: CountryRepository,
) {
    suspend operator fun invoke(countryName: String): String? {
        return countryRepository.getAllCountries()
            .firstOrNull { it.countryName.equals(countryName, ignoreCase = true) }
            ?.countryCode
    }
}