package com.domain.media.useCase

import com.domain.media.entity.Category
import com.domain.media.repository.MoviesCategoriesRepository

class GetMoviesCategoriesUseCase(
    private val categoriesRepository: MoviesCategoriesRepository
) {
    suspend operator fun invoke(): List<Category> {
        return categoriesRepository.getMoviesCategories()
    }
}