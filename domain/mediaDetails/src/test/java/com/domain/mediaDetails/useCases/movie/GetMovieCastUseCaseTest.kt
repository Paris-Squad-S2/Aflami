package com.domain.mediaDetails.useCases.movie

import com.domain.mediaDetails.repository.MovieRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import testUtils.fakeCast
import kotlin.test.Test
import kotlin.test.assertEquals


class GetMovieCastUseCaseTest {
    private lateinit var getMovieCastUseCase: GetMovieCastUseCase
    private val movieRepository: MovieRepository = mockk(relaxed = true)

    @Before
    fun setup() {
        getMovieCastUseCase = GetMovieCastUseCase(movieRepository)
    }

    @Test
    fun `should return movie cast from repository`() = runTest {
        // Given
        val movieId = 1

        // when
        coEvery { movieRepository.getMovieCast(movieId) } returns fakeCast

        // Then
        val result = getMovieCastUseCase(movieId)
        assertEquals(result, fakeCast)

    }

    @Test
    fun `should return empty list when no cast found`() = runTest{
        // Given
        val movieId = 1

        // when
        coEvery { movieRepository.getMovieCast(movieId) } returns emptyList()

        // Then
        val result = getMovieCastUseCase(movieId)
        assertEquals(result, emptyList())
    }
}