package com.domain.search.useCases

class AutoCompleteCountry (
    // private val countryRepository: CountryRepository
){
    suspend operator fun invoke(query: String): List<String> {
        return TemporaryFakeData.countryList.filter { it.contains(query, ignoreCase = true) }
        // return countryRepository.getAllCountries().filter { it.contains(query, ignoreCase = true) }
    }
}