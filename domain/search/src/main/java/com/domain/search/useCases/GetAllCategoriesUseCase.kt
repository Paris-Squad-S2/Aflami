package com.domain.search.useCases

import com.domain.search.model.CategoryModel
import com.domain.search.repository.CategoriesRepository

class GetAllCategoriesUseCase(
    private val categoriesRepository: CategoriesRepository,
) {
    suspend operator fun invoke(): List<CategoryModel> {
        return categoriesRepository.getAllCategories()
    }
}