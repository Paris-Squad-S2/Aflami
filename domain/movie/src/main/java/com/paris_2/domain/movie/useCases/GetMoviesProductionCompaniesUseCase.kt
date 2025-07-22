package com.paris_2.domain.movie.useCases

import com.paris_2.domain.movie.model.MovieProductionCompany
import com.paris_2.domain.movie.repository.MovieRepository

class GetMoviesProductionCompaniesUseCase(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int): List<MovieProductionCompany> {
        return movieRepository.getCompanyProducts(movieId)
    }
}