package com.domain.mediaDetails.useCases.movie

import com.domain.mediaDetails.repository.MovieRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import testUtils.fakeMovies
import kotlin.test.assertEquals

class GetMovieRecommendationsUseCaseTest {
    private lateinit var getMovieRecommendationsUseCase: GetMovieRecommendationsUseCase
    private val movieRepository: MovieRepository = mockk(relaxed = true)

    @Before
    fun setup() {
        getMovieRecommendationsUseCase = GetMovieRecommendationsUseCase(movieRepository)
    }

    @Test
    fun `should return movie recommendation from repository`() = runTest {
        // Given
        val movieId = 1
        val page = 1

        // when
        coEvery { movieRepository.getMovieRecommendations(movieId,page) } returns fakeMovies

        // Then
        val result = getMovieRecommendationsUseCase(movieId,page)
        assertEquals(result, fakeMovies)

    }

    @Test
    fun `should return empty list when no cast found`() = runTest{
        // Given
        val movieId = 1
        val page = 1
        // when
        coEvery { movieRepository.getMovieRecommendations(movieId,page) } returns emptyList()

        // Then
        val result = getMovieRecommendationsUseCase(movieId,page)
        assertEquals(result, emptyList())
    }

}