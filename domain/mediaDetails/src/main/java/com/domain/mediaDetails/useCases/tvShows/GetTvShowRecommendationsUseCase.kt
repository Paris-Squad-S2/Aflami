package com.domain.mediaDetails.useCases.tvShows

import com.domain.mediaDetails.model.TvShow
import com.domain.mediaDetails.model.TvShowSimilar
import com.domain.mediaDetails.repository.TvShowRepository

class GetTvShowRecommendationsUseCase(
    private val tvShowRepository: TvShowRepository
) {
    suspend operator fun invoke(tvShowId: Int, page: Int): List<TvShowSimilar> {
        return tvShowRepository.getTvShowRecommendations(tvShowId, page)
    }
}