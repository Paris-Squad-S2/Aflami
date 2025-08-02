package com.paris_2.domain.media.useCase

import com.domain.media.entity.Category
import com.domain.media.repository.CategoriesRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GetAllCategoriesUseCaseTest {

    private lateinit var categoriesRepository: CategoriesRepository
    private lateinit var getAllCategoriesUseCase: GetAllCategoriesUseCase

    @BeforeEach

    fun setUp() {
        categoriesRepository = mockk()
        getAllCategoriesUseCase = GetAllCategoriesUseCase(categoriesRepository)
    }


    @Test
    fun `should return correct number of categories from repository`() = runTest {
        //Given
        coEvery { categoriesRepository.getAllCategories() } returns sampleCategories

        //When
        val result = getAllCategoriesUseCase()

        //Then
        assertEquals(3, result.size)
    }

    @Test
    fun `should call getAllCategories exactly once`() = runTest {
        // Given
        coEvery { categoriesRepository.getAllCategories() } returns sampleCategories

        // When
        getAllCategoriesUseCase()

        // Then
        coVerify(exactly = 1) { categoriesRepository.getAllCategories() }
    }

    @Test
    fun `should return expected categories from repository`() = runTest {
        //Given
        coEvery { categoriesRepository.getAllCategories() } returns sampleCategories

        //When
        val result = getAllCategoriesUseCase()

        //Then
        assertEquals(sampleCategories, result)
    }

    @Test
    fun `should return empty list when repository returns no categories`() = runTest {
        //Given
        coEvery { categoriesRepository.getAllCategories() } returns emptyList()

        //When
        val result = getAllCategoriesUseCase()

        //Then
        assertTrue { result.isEmpty() }
    }

    @Test
    fun `should call getAllCategories when no categories found`() = runTest {
        // Given
        coEvery { categoriesRepository.getAllCategories() } returns emptyList()

        // When
        getAllCategoriesUseCase()

        // Then
        coVerify(exactly = 1) { categoriesRepository.getAllCategories() }
    }

    companion object {
        private val sampleCategories = listOf(
            Category(id = 1, name = "Romance"),
            Category(id = 2, name = "Science Fiction"),
            Category(id = 3, name = "Family")
        )
    }

}