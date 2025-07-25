package com.domain.home.usecase

import com.domain.home.repository.MoviesCategoriesRepository
import com.domain.home.model.Category

class GetMoviesCategoriesUseCase(
    private val categoriesRepository: MoviesCategoriesRepository
) {
    suspend operator fun invoke(): List<Category> {
        return categoriesRepository.getMoviesCategories()
    }
}