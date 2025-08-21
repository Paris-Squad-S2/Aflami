package com.paris.domain.media.useCase.tvShows

import com.paris.domain.media.entity.Image
import com.paris.domain.media.repository.TvShowRepository

class GetTvShowGalleryUseCase(
    private val tvShowRepository: TvShowRepository
) {
    suspend operator fun invoke(tvShowId: Int): List<Image> {
        return tvShowRepository.getTvShowGallery(tvShowId)
    }
}