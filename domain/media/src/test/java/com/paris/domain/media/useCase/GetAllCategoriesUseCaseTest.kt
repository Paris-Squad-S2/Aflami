package com.paris.domain.media.useCase

import com.google.common.truth.Truth.assertThat
import com.paris.domain.media.entity.Category
import com.paris.domain.media.repository.CategoriesRepository
import com.paris.domain.media.useCase.search.GetAllCategoriesUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

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
        // Given
        coEvery { categoriesRepository.getAllCategories() } returns sampleCategories

        // When
        val result = getAllCategoriesUseCase()

        // Then
        assertThat(result).hasSize(3)
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
        // Given
        coEvery { categoriesRepository.getAllCategories() } returns sampleCategories

        // When
        val result = getAllCategoriesUseCase()

        // Then
        assertThat(result).isEqualTo(sampleCategories)
    }

    @Test
    fun `should return empty list when repository returns no categories`() = runTest {
        // Given
        coEvery { categoriesRepository.getAllCategories() } returns emptyList()

        // When
        val result = getAllCategoriesUseCase()

        // Then
        assertThat(result).isEmpty()
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
            Category.Action,
            Category.Drama,
            Category.Comedy
        )
    }
}