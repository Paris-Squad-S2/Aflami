package com.paris_2.domain.media.useCase

import com.paris_2.domain.media.entity.Media
import com.paris_2.domain.media.entity.MediaType
import com.paris_2.domain.media.repository.SearchMediaRepository
import javax.inject.Inject

class GetMoviesOnlyByCountryNameUseCase @Inject constructor(
    private val searchMediaRepository: SearchMediaRepository,
) {
    suspend operator fun invoke(countryName: String, page: Int): List<Media> {
        return searchMediaRepository.getMoviesByCountry(countryName, page)
            .filter { it.type == MediaType.Movie }

    }
}