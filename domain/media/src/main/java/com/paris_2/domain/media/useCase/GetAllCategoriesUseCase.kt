package com.paris_2.domain.media.useCase

import com.paris_2.domain.media.entity.Category
import com.paris_2.domain.media.repository.CategoriesRepository
import javax.inject.Inject

class GetAllCategoriesUseCase @Inject constructor(
    private val categoriesRepository: CategoriesRepository,
) {
    suspend operator fun invoke(): List<Category> {
        return categoriesRepository.getAllCategories()
    }
}