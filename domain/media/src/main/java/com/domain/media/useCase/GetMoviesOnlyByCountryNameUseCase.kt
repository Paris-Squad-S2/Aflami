package com.domain.media.useCase

import com.domain.media.model.Media
import com.domain.media.model.MediaType
import com.domain.media.repository.SearchMediaRepository

class GetMoviesOnlyByCountryNameUseCase(
    private val searchMediaRepository: SearchMediaRepository,
) {
    suspend operator fun invoke(countryName: String, page: Int): List<Media> {
        return searchMediaRepository.getMoviesByCountry(countryName, page)
            .filter { it.type == MediaType.MOVIE }
    }
}