package com.domain.search.useCases

import com.domain.search.repository.CountryRepository

class GetCountryCodeByNameUseCase(
    private val countryRepository: CountryRepository,
) {
    suspend operator fun invoke(countryName: String): String? {
        val allCountries = countryRepository.getAllCountries()

        return allCountries.firstOrNull {
            it.countryCode.equals(countryName, ignoreCase = true) ||
                    it.englishName.contains(countryName, ignoreCase = true) ||
                    it.arabicName.contains(countryName, ignoreCase = true)
        }?.countryCode
    }
}