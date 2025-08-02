package com.domain.mediaDetails.useCase.tvShows

import com.domain.mediaDetails.entity.Image
import com.domain.mediaDetails.repository.TvShowRepository

class GetTvShowGalleryUseCase(
    private val tvShowRepository: TvShowRepository
) {
    suspend operator fun invoke(tvShowId: Int): List<Image> {
        return tvShowRepository.getTvShowGallery(tvShowId)
    }
}