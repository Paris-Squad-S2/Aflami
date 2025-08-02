package com.domain.media.useCase.tvShows

import com.domain.media.entity.ProductionCompany
import com.domain.media.repository.TvShowRepository

class GetTvShowsProductionCompaniesUseCase(
    private val tvShowRepository: TvShowRepository
) {
    suspend operator fun invoke(tvShowId: Int): List<ProductionCompany> {
        return tvShowRepository.getCompanyProducts(tvShowId)
    }
}