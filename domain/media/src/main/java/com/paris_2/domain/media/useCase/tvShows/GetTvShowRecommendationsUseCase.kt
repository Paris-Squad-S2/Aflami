package com.paris_2.domain.media.useCase.tvShows

import com.paris_2.domain.media.entity.TvShowSimilar
import com.paris_2.domain.media.repository.TvShowRepository

class GetTvShowRecommendationsUseCase(
    private val tvShowRepository: TvShowRepository
) {
    suspend operator fun invoke(tvShowId: Int, page: Int): List<TvShowSimilar> {
        return tvShowRepository.getTvShowRecommendations(tvShowId, page)
    }
}