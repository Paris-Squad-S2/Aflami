package com.paris_2.domain.tvshow.useCases

import com.paris_2.domain.tvshow.model.TvShowSimilar
import com.paris_2.domain.tvshow.repository.TvShowRepository

class GetTvShowRecommendationsUseCase(
    private val tvShowRepository: TvShowRepository
) {
    suspend operator fun invoke(tvShowId: Int, page: Int): List<TvShowSimilar> {
        return tvShowRepository.getTvShowRecommendations(tvShowId, page)
    }
}