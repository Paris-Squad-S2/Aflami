package com.domain.mediaDetails.useCases.tvShows

import com.domain.mediaDetails.model.Cast
import com.domain.mediaDetails.repository.TvShowRepository

class GetTvShowCastUseCase(
    private val tvShowRepository: TvShowRepository
) {
    suspend operator fun invoke(tvShowId: Int): List<Cast>{
        return tvShowRepository.getTvShowCast(tvShowId)
    }
}