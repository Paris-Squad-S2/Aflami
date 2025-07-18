package com.domain.mediaDetails.useCases.tvShows

import com.domain.mediaDetails.model.Gallery
import com.domain.mediaDetails.repository.TvShowRepository

class GetTvShowGalleryUseCase(
    private val tvShowRepository: TvShowRepository
) {
    suspend operator fun invoke(tvShowId: Int): Gallery {
        return tvShowRepository.getTvShowGallery(tvShowId)
    }
}