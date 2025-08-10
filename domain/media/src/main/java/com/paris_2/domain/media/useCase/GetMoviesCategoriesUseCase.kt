package com.paris_2.domain.media.useCase

import com.paris_2.domain.media.entity.Category
import com.paris_2.domain.media.repository.MoviesCategoriesRepository
import javax.inject.Inject

class GetMoviesCategoriesUseCase @Inject constructor(
    private val categoriesRepository: MoviesCategoriesRepository
) {
    suspend operator fun invoke(): List<Category> {
        return categoriesRepository.getMoviesCategories()
    }
}