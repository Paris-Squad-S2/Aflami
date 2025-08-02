package com.domain.mediaDetails.useCase.tvShows

import com.domain.media.entity.TvShowSimilar
import com.domain.media.repository.TvShowRepository

class GetTvShowRecommendationsUseCase(
    private val tvShowRepository: TvShowRepository
) {
    suspend operator fun invoke(tvShowId: Int, page: Int): List<TvShowSimilar> {
        return tvShowRepository.getTvShowRecommendations(tvShowId, page)
    }
}