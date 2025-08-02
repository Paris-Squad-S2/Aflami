package com.domain.media.useCase.movie

import com.domain.media.entity.ProductionCompany
import com.domain.media.repository.MovieRepository

class GetMoviesProductionCompaniesUseCase(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int): List<ProductionCompany> {
        return movieRepository.getCompanyProducts(movieId)
    }
}