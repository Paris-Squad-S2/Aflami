package com.paris.domain.media.useCase.search

import com.paris.domain.media.entity.Category
import com.paris.domain.media.repository.CategoriesRepository

class GetAllCategoriesUseCase(
    private val categoriesRepository: CategoriesRepository,
) {
    suspend operator fun invoke(): List<Category> {
        return categoriesRepository.getAllCategories()
    }
}