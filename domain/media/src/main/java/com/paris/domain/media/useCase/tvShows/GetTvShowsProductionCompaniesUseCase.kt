package com.paris.domain.media.useCase.tvShows

import com.paris.domain.media.entity.ProductionCompany
import com.paris.domain.media.repository.TvShowRepository

class GetTvShowsProductionCompaniesUseCase(
    private val tvShowRepository: TvShowRepository
) {
    suspend operator fun invoke(tvShowId: Int): List<ProductionCompany> {
        return tvShowRepository.getCompanyProducts(tvShowId)
    }
}