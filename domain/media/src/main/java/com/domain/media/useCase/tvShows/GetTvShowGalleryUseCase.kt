package com.domain.media.useCase.tvShows

import com.domain.media.entity.Image
import com.domain.media.repository.TvShowRepository

class GetTvShowGalleryUseCase(
    private val tvShowRepository: TvShowRepository
) {
    suspend operator fun invoke(tvShowId: Int): List<Image> {
        return tvShowRepository.getTvShowGallery(tvShowId)
    }
}