package com.paris_2.domain.media.useCase.tvShows

import com.paris_2.domain.media.entity.ProductionCompany
import com.paris_2.domain.media.repository.TvShowRepository
import javax.inject.Inject

class GetTvShowsProductionCompaniesUseCase @Inject constructor(
    private val tvShowRepository: TvShowRepository
) {
    suspend operator fun invoke(tvShowId: Int): List<ProductionCompany> {
        return tvShowRepository.getCompanyProducts(tvShowId)
    }
}