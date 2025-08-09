package com.paris_2.domain.media.useCase.movie

import com.paris_2.domain.media.repository.MovieRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import com.paris_2.domain.media.testUtils.fakeMovie
import com.google.common.truth.Truth.assertThat

class GetMovieDetailsUseCaseTest {
    private lateinit var getMovieDetailsUseCase: GetMovieDetailsUseCase
    private val movieRepository: MovieRepository = mockk(relaxed = true)

    @BeforeEach
    fun setup() {
        getMovieDetailsUseCase = GetMovieDetailsUseCase(movieRepository)
    }

    @Test
    fun `should return movie details from repository`() = runTest {
        // Given
        coEvery { movieRepository.getMovieDetails(movieId) } returns fakeMovie

        // When
        val result = getMovieDetailsUseCase(movieId)

        // Then
        assertThat(result).isEqualTo(fakeMovie)
    }

    @Test
    fun `should verify repository interaction when getting movie details`() = runTest {
        // Given
        coEvery { movieRepository.getMovieDetails(movieId) } returns fakeMovie

        // When
        getMovieDetailsUseCase(movieId)

        // Then
        coVerify(exactly = 1) { movieRepository.getMovieDetails(movieId) }
    }

    private companion object {
        const val movieId = 1
    }
}
