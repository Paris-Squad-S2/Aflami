package com.domain.mediaDetails.useCases.tvShows

import com.domain.mediaDetails.repository.TvShowRepository

class AddTvShowToFavoriteUseCase(
    private val tvShowRepository: TvShowRepository
) {
    suspend operator fun invoke(tvShowId: Int){
        return tvShowRepository.addTvShowToFavorite(tvShowId)
    }
}