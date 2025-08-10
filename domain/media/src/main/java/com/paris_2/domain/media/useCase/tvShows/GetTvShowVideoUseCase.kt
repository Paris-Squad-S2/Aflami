package com.paris_2.domain.media.useCase.tvShows

import com.paris_2.domain.media.entity.TvShowVideo
import com.paris_2.domain.media.repository.TvShowRepository
import javax.inject.Inject

class GetTvShowVideoUseCase @Inject constructor(
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