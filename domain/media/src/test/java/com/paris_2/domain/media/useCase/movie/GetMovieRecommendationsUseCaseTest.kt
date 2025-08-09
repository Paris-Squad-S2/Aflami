package com.paris_2.domain.media.useCase.movie

import com.google.common.truth.Truth.assertThat
import com.paris_2.domain.media.repository.MovieRepository
import com.paris_2.domain.media.testUtils.fakeMovieSimilar
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetMovieRecommendationsUseCaseTest {

    private lateinit var getMovieRecommendationsUseCase: GetMovieRecommendationsUseCase
    private val movieRepository: MovieRepository = mockk(relaxed = true)

    @BeforeEach
    fun setup() {
        getMovieRecommendationsUseCase = GetMovieRecommendationsUseCase(movieRepository)
    }

    @Test
    fun `should return movie recommendation from repository`() = runTest {
        // Given
        coEvery { movieRepository.getMovieRecommendations(movieId, page) } returns fakeMovieSimilar

        // When
        val result = getMovieRecommendationsUseCase(movieId, page)

        // Then
        assertThat(result).isEqualTo(fakeMovieSimilar)
    }

    @Test
    fun `should return empty list when no recommendations found`() = runTest {
        // Given
        coEvery { movieRepository.getMovieRecommendations(movieId, page) } returns emptyList()

        // When
        val result = getMovieRecommendationsUseCase(movieId, page)

        // Then
        assertThat(result).isEmpty()
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

    private companion object {
        const val movieId = 1
        const val page = 1
    }
}