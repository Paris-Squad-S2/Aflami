package com.paris.domain.media.useCase

import com.google.common.truth.Truth.assertThat
import com.paris.domain.media.useCase.search.GetAllCategoriesUseCase
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetAllCategoriesUseCaseTest {

    private lateinit var getAllCategoriesUseCase: GetAllCategoriesUseCase

    @BeforeEach
    fun setUp() {
        getAllCategoriesUseCase = GetAllCategoriesUseCase()
    }

    @Test
    fun `should return correct number of categories from repository`() = runTest {
        // When
        val result = getAllCategoriesUseCase()

        // Then
        assertThat(result).hasSize(27)
    }
}