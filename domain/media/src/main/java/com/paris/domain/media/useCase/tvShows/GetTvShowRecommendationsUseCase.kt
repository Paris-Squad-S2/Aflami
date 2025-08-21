package com.paris.domain.media.useCase.tvShows

import com.paris.domain.media.entity.TvShowSimilar
import com.paris.domain.media.repository.TvShowRepository

class GetTvShowRecommendationsUseCase(
    private val tvShowRepository: TvShowRepository
) {
    suspend operator fun invoke(tvShowId: Int, page: Int): List<TvShowSimilar> {
        return tvShowRepository.getTvShowRecommendations(tvShowId, page)
    }
}