package com.domain.search.useCase

import com.domain.search.model.Category
import com.domain.search.repository.CategoriesRepository

class GetAllCategoriesUseCase(
    private val categoriesRepository: CategoriesRepository,
) {
    suspend operator fun invoke(): List<Category> {
        return categoriesRepository.getAllCategories()
    }
}