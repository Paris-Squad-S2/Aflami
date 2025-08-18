package com.paris_2.domain.media.useCase.tvShows

import com.paris_2.domain.media.entity.MediaVideo
import com.paris_2.domain.media.repository.TvShowRepository

class GetEpisodeVideoUseCase(
    private val tvShowRepository: TvShowRepository
) {
    suspend operator fun invoke(tvShowId: Int,seasonNumber: Int,episodeNumber: Int): MediaVideo {
        return tvShowRepository.getTrailerVideoForEpisode(tvShowId,seasonNumber,episodeNumber)
            .first { it.site.trim().lowercase() == SITE }

    }
    companion object{
        private const val SITE = "youtube"
    }
}