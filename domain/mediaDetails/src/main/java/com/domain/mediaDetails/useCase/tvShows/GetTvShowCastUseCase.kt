package com.domain.mediaDetails.useCase.tvShows

import com.domain.mediaDetails.entity.Cast
import com.domain.mediaDetails.repository.TvShowRepository

class GetTvShowCastUseCase(
    private val tvShowRepository: TvShowRepository
) {
    suspend operator fun invoke(tvShowId: Int): List<Cast>{
        return tvShowRepository.getTvShowCast(tvShowId)
    }
}