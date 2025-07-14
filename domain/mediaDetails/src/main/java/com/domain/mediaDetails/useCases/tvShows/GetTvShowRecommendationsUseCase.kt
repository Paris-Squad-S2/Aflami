package com.domain.mediaDetails.useCases.tvShows

import com.domain.mediaDetails.model.TvShow
import com.domain.mediaDetails.repository.TvShowRepository

class GetTvShowRecommendationsUseCase(
    private val tvShowRepository: TvShowRepository
) {
    suspend operator fun invoke(tvShowId: Int): List<TvShow>{
        return tvShowRepository.getTvShowRecommendations(tvShowId)
    }
}