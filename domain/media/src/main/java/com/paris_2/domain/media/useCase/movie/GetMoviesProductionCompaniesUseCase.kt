package com.paris_2.domain.media.useCase.movie

import com.paris_2.domain.media.entity.ProductionCompany
import com.paris_2.domain.media.repository.MovieRepository
import javax.inject.Inject

class GetMoviesProductionCompaniesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int): List<ProductionCompany> {
        return movieRepository.getCompanyProducts(movieId)
    }
}