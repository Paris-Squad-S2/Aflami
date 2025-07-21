package com.domain.home.usecase

import com.domain.home.model.Media
import com.domain.home.repository.MediaRepository

class FilterUpComingMediaByGenreUseCase(
    private val mediaRepository : MediaRepository
) {
    suspend operator fun invoke(genreId: Int): List<Media>{
        return mediaRepository.getUpComingMedia().filter { media ->
            genreId in media.genreIds
        }
    }
}