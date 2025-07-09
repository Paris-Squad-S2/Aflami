package com.domain.search.useCases

import com.domain.search.model.Media
import com.domain.search.repository.SearchMediaRepository

class GetMoviesOnlyByCountryName(
    private val searchMediaRepository: SearchMediaRepository,
) {
    suspend operator fun invoke(countryName: String): List<Media> {
        return searchMediaRepository.getMoviesByCountry(countryName)
    }
}