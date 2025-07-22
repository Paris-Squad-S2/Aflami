package com.paris_2.domain.tvshow.useCases

import com.paris_2.domain.tvshow.repository.TvShowRepository

class AddTvShowToFavoriteUseCase(
    private val tvShowRepository: TvShowRepository
) {
    suspend operator fun invoke(tvShowId: Int){
        return tvShowRepository.addTvShowToFavorite(tvShowId)
    }
}