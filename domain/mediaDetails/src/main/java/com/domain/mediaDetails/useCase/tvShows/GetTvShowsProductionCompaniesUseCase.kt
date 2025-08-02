package com.domain.mediaDetails.useCase.tvShows

import com.domain.mediaDetails.entity.ProductionCompany
import com.domain.mediaDetails.repository.TvShowRepository

class GetTvShowsProductionCompaniesUseCase(
    private val tvShowRepository: TvShowRepository
) {
    suspend operator fun invoke(tvShowId: Int): List<ProductionCompany> {
        return tvShowRepository.getCompanyProducts(tvShowId)
    }
}