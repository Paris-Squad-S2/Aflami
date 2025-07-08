package com.domain.search.useCases

import com.domain.search.model.Media

class GetMoviesOnlyByCountryName (
    // private val movieRepository: MovieRepository
){

    suspend operator fun invoke(countryName: String): List<Media> {
        return TemporaryFakeData.MoviesList
        // return mediaRepository.getMoviesByCountry(countryName)
    }
}