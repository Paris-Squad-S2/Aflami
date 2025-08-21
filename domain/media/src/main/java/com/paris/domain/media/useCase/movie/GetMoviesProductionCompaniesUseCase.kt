package com.paris.domain.media.useCase.movie

import com.paris.domain.media.entity.ProductionCompany
import com.paris.domain.media.repository.MovieRepository

class GetMoviesProductionCompaniesUseCase(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int): List<ProductionCompany> {
        return movieRepository.getCompanyProducts(movieId)
    }
}