package com.domain.media.useCase.tvShows

import com.domain.media.entity.EpisodeVideo
import com.domain.media.repository.TvShowRepository

class GetEpisodeVideoUseCase(
    private val tvShowRepository: TvShowRepository
) {
    suspend operator fun invoke(tvShowId: Int,seasonNumber: Int,episodeNumber: Int): EpisodeVideo {
        return tvShowRepository.getTrailerVideoForEpisode(tvShowId,seasonNumber,episodeNumber)
            .first { it.site.trim().lowercase() == SITE }

    }
    companion object{
        private const val SITE = "youtube"
    }
}