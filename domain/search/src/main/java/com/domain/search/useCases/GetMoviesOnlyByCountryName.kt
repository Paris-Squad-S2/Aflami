package com.domain.search.useCases

import com.domain.search.model.Media

class GetMoviesOnlyByCountryName (
    // private val searchMediaRepository: SearchMediaRepository
){

    suspend operator fun invoke(countryName: String): List<Media> {
        return TemporaryFakeData.MoviesList
        // return searchMediaRepository.getMoviesByCountry(countryName)
    }
}