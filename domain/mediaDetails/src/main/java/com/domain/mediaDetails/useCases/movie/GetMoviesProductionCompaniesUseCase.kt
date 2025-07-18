package com.domain.mediaDetails.useCases.movie

import com.domain.mediaDetails.model.ProductionCompany
import com.domain.mediaDetails.repository.MovieRepository

class GetMoviesProductionCompaniesUseCase(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int): List<ProductionCompany> {
        return movieRepository.getCompanyProducts(movieId)
    }
}