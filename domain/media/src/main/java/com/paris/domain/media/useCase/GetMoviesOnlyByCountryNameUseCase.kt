package com.paris.domain.media.useCase

import com.paris.domain.media.entity.Media
import com.paris.domain.media.entity.MediaType
import com.paris.domain.media.repository.SearchMediaRepository

class GetMoviesOnlyByCountryNameUseCase(
    private val searchMediaRepository: SearchMediaRepository,
) {
    suspend operator fun invoke(countryName: String, page: Int): List<Media> {
        return searchMediaRepository.getMoviesByCountry(countryName, page)
            .filter { it.type == MediaType.Movie }
    }
}