package com.domain.mediaDetails.useCase.tvShows

import com.domain.mediaDetails.entity.TvShowVideo
import com.domain.mediaDetails.repository.TvShowRepository

class GetTvShowVideoUseCase(
    private val tvShowRepository: TvShowRepository
) {
    suspend operator fun invoke(tvShowId: Int): TvShowVideo {
        return tvShowRepository.getTrailerVideoForTvShow(tvShowId)
            .first { it.site.trim().lowercase() == SITE }

    }
    companion object{
        private const val SITE = "youtube"
    }
}