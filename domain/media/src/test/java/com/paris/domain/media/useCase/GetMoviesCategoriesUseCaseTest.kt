package com.paris.domain.media.useCase

import com.google.common.truth.Truth.assertThat
import com.paris.domain.media.useCase.movie.GetMoviesCategoriesUseCase
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class GetMoviesCategoriesUseCaseTest {
    private val useCase = GetMoviesCategoriesUseCase()

    @Test
    fun `invoke returns movies categories from repository`() = runTest {
        val result = useCase()
        assertThat(result).hasSize(19)
    }
}
