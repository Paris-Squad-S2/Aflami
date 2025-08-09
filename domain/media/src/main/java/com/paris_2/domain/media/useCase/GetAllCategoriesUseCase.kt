package com.paris_2.domain.media.useCase

import com.paris_2.domain.media.entity.Genre
import com.paris_2.domain.media.repository.CategoriesRepository

class GetAllCategoriesUseCase(
    private val categoriesRepository: CategoriesRepository,
) {
    suspend operator fun invoke(): List<Genre> {
        return categoriesRepository.getAllCategories()
    }
}