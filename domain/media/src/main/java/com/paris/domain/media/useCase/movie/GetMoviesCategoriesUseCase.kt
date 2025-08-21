package com.paris.domain.media.useCase.movie

import com.paris.domain.media.entity.Category
import com.paris.domain.media.repository.MoviesCategoriesRepository

class GetMoviesCategoriesUseCase(
    private val categoriesRepository: MoviesCategoriesRepository
) {
    suspend operator fun invoke(): List<Category> {
        return categoriesRepository.getMoviesCategories()
    }
}