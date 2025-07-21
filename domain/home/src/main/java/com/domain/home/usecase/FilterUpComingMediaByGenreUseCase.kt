package com.domain.home.usecase

import com.domain.home.model.UpComingMedia
import com.domain.home.repository.MediaRepository

class FilterUpComingMediaByGenreUseCase(
    private val mediaRepository : MediaRepository
) {
    suspend operator fun invoke(genreId: Int): List<UpComingMedia>{
        return mediaRepository.getUpComingMedia().filter { media ->
            media.genres.any(){ genre ->
                genre.id == genreId
            }
        }
    }
}