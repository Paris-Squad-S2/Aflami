package com.domain.mediaDetails.useCase.movie

import com.domain.mediaDetails.repository.MovieRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import testUtils.fakeMovie
import kotlin.test.assertEquals

class GetMovieDetailsUseCaseTest {
    private lateinit var getMovieDetailsUseCase: GetMovieDetailsUseCase
    private val movieRepository: MovieRepository = mockk(relaxed = true)

    @Before
    fun setup() {
        getMovieDetailsUseCase = GetMovieDetailsUseCase(movieRepository)
    }

    @Test
    fun `should return movie details from repository`() = runTest {
        // Given
        coEvery { movieRepository.getMovieDetails(movieId) } returns fakeMovie

        // when
        val result = getMovieDetailsUseCase(movieId)

        // Then
        assertEquals(result, fakeMovie)

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

    private companion object{
        val movieId = 1
    }
}