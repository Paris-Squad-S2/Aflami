package com.domain.mediaDetails.useCase.tvShows

import com.domain.mediaDetails.model.EpisodeVideo
import com.domain.mediaDetails.model.TvShowVideo
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