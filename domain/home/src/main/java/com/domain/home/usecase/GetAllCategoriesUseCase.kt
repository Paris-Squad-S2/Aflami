package com.domain.home.usecase

import com.domain.home.repository.CategoriesRepository
import com.domain.home.model.Category

class GetAllCategoriesUseCase(
    private val categoriesRepository: CategoriesRepository
) {
    suspend operator fun invoke(): List<Category> {
        return categoriesRepository.getAllCategories()
    }
}