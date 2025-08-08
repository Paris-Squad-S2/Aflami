package com.paris_2.domain.media.useCase.tvShows

import com.paris_2.domain.media.entity.Image
import com.paris_2.domain.media.repository.TvShowRepository

class GetTvShowGalleryUseCase(
    private val tvShowRepository: TvShowRepository
) {
    suspend operator fun invoke(tvShowId: Int): List<Image> {
        return tvShowRepository.getTvShowGallery(tvShowId)
    }
}