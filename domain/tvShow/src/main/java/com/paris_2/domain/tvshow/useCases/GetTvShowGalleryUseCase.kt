package com.paris_2.domain.tvshow.useCases

import com.paris_2.domain.tvshow.model.TvShowGallery
import com.paris_2.domain.tvshow.repository.TvShowRepository

class GetTvShowGalleryUseCase(
    private val tvShowRepository: TvShowRepository
) {
    suspend operator fun invoke(tvShowId: Int): TvShowGallery {
        return tvShowRepository.getTvShowGallery(tvShowId)
    }
}