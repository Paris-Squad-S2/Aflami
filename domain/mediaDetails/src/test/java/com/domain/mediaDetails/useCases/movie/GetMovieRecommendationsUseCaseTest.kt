package com.domain.mediaDetails.useCases.movie

import com.domain.mediaDetails.repository.MovieRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import testUtils.fakeMovieSimilar
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
        coEvery { movieRepository.getMovieRecommendations(movieId,page) } returns fakeMovieSimilar

        // when
        val result = getMovieRecommendationsUseCase(movieId,page)

        // Then
        assertEquals(result, fakeMovieSimilar)

    }

    @Test
    fun `should return empty list when no cast found`() = runTest{
        // Given
        coEvery { movieRepository.getMovieRecommendations(movieId,page) } returns emptyList()

        // when
        val result = getMovieRecommendationsUseCase(movieId,page)

        // Then
        assertEquals(result, emptyList())
    }

    @Test
    fun `should call repository method when use case is invoked`() = runTest {
        // Given
        coEvery { movieRepository.getMovieRecommendations(movieId, page) } returns fakeMovieSimilar

        // When
        getMovieRecommendationsUseCase(movieId, page)

        // Then
        coVerify(exactly = 1) { movieRepository.getMovieRecommendations(movieId, page) }
    }

    private companion object{
        val movieId = 1
        val page = 1
    }

}