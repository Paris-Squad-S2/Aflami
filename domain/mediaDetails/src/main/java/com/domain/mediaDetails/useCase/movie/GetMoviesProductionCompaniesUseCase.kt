package com.domain.mediaDetails.useCase.movie

import com.domain.mediaDetails.entity.ProductionCompany
import com.domain.mediaDetails.repository.MovieRepository

class GetMoviesProductionCompaniesUseCase(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int): List<ProductionCompany> {
        return movieRepository.getCompanyProducts(movieId)
    }
}