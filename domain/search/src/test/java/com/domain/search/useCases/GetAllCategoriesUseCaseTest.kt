package com.domain.search.useCases

import com.domain.search.model.CategoryModel
import com.domain.search.repository.CategoriesRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GetAllCategoriesUseCaseTest {

    private lateinit var categoriesRepository: CategoriesRepository
    private lateinit var useCase: GetAllCategoriesUseCase

    @BeforeEach

    fun setUp() {
        categoriesRepository = mockk()
        useCase = GetAllCategoriesUseCase(categoriesRepository)
    }


    @Test
    fun `invoke should return all categories from repository`() = runTest {
        //Given
        val categories = listOf(
            CategoryModel(id = 1, name = "Romance"),
            CategoryModel(id = 2, name = "Science Fiction"),
            CategoryModel(id = 3, name = "Family")
        )

        coEvery { categoriesRepository.getAllCategories() } returns categories

        //When
        val result = useCase()

        //Then
        assertEquals(3, result.size)
        assertEquals(categories, result)

    }

    @Test
    fun `invoke should return empty list when repository returns no categories`() = runTest {

        //Given
        coEvery { categoriesRepository.getAllCategories() } returns emptyList()

        //When
        val result = useCase()

        //Then
        assertTrue { result.isEmpty() }

    }

}