package com.paris_2.domain.media.useCase

import com.paris_2.domain.media.repository.CountryRepository
import javax.inject.Inject

class GetCountryCodeByNameUseCase @Inject constructor(
    private val countryRepository: CountryRepository,
) {
    suspend operator fun invoke(countryName: String): String? {
        val allCountries = countryRepository.getAllCountries()

        return allCountries.firstOrNull {
            it.countryCode.equals(countryName, ignoreCase = true)
        }?.countryCode ?: allCountries.firstOrNull {
            it.englishName.contains(countryName, ignoreCase = true) ||
                    it.arabicName.contains(countryName, ignoreCase = true)
        }?.countryCode
    }
}