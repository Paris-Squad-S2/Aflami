package com.domain.mediaDetails.useCase.tvShows

import com.domain.mediaDetails.entity.TvShowSimilar
import com.domain.mediaDetails.repository.TvShowRepository

class GetTvShowRecommendationsUseCase(
    private val tvShowRepository: TvShowRepository
) {
    suspend operator fun invoke(tvShowId: Int, page: Int): List<TvShowSimilar> {
        return tvShowRepository.getTvShowRecommendations(tvShowId, page)
    }
}