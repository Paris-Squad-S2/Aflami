package com.paris_2.domain.media.useCase

import com.paris_2.domain.media.entity.Genre
import com.paris_2.domain.media.repository.MoviesCategoriesRepository

class GetMoviesCategoriesUseCase(
    private val categoriesRepository: MoviesCategoriesRepository
) {
    suspend operator fun invoke(): List<Genre> {
        return categoriesRepository.getMoviesCategories()
    }
}