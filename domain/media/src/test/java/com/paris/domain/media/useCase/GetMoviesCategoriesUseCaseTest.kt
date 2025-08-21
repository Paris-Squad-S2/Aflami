package com.paris.domain.media.useCase

import com.google.common.truth.Truth.assertThat
import com.paris.domain.media.entity.Category
import com.paris.domain.media.repository.MoviesCategoriesRepository
import com.paris.domain.media.useCase.movie.GetMoviesCategoriesUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class GetMoviesCategoriesUseCaseTest {
    private val repository: MoviesCategoriesRepository = mockk()
    private val useCase = GetMoviesCategoriesUseCase(repository)

    private val fakeCategories = listOf(
        Category.Action,
        Category.Adventure,
        Category.Animation,
        Category.Comedy
    )

    @Test
    fun `invoke returns movies categories from repository`() = runTest {
        coEvery { repository.getMoviesCategories() } returns fakeCategories
        val result = useCase()
        assertThat(result).isEqualTo(fakeCategories)
    }
}
