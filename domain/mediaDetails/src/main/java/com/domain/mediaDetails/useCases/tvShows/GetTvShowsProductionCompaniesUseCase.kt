package com.domain.mediaDetails.useCases.tvShows

import com.domain.mediaDetails.model.ProductionCompany
import com.domain.mediaDetails.repository.TvShowRepository

class GetTvShowsProductionCompaniesUseCase(
    private val tvShowRepository: TvShowRepository
) {
    suspend operator fun invoke(tvShowId: Int): List<ProductionCompany> {
        return tvShowRepository.getCompanyProducts(tvShowId)
    }
}