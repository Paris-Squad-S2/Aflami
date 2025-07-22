package com.paris_2.domain.tvshow.useCases

import com.paris_2.domain.tvshow.model.TvShowProductionCompany
import com.paris_2.domain.tvshow.repository.TvShowRepository

class GetTvShowsProductionCompaniesUseCase(
    private val tvShowRepository: TvShowRepository
) {
    suspend operator fun invoke(tvShowId: Int): List<TvShowProductionCompany> {
        return tvShowRepository.getCompanyProducts(tvShowId)
    }
}