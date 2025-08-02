package com.paris_2.domain.media.useCase

import com.domain.media.entity.Category
import com.domain.media.repository.MoviesCategoriesRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import com.google.common.truth.Truth.assertThat

class GetMoviesCategoriesUseCaseTest {
    private val repository: MoviesCategoriesRepository = mockk()
    private val useCase = GetMoviesCategoriesUseCase(repository)

    private val fakeCategories = listOf(
        Category(id = 28, name = "Action"),
        Category(id = 18, name = "Drama"),
        Category(id = 35, name = "Comedy")
    )

    @Test
    fun `invoke returns movies categories from repository`() = runTest {
        coEvery { repository.getMoviesCategories() } returns fakeCategories
        val result = useCase()
        assertThat(result).isEqualTo(fakeCategories)
    }
}
