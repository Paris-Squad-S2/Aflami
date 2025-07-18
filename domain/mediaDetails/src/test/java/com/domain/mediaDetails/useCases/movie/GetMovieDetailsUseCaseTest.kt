package com.domain.mediaDetails.useCases.movie

import com.domain.mediaDetails.repository.MovieRepository
import io.mockk.coEvery
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
        val movieId = 1

        // when
        coEvery { movieRepository.getMovieDetails(movieId) } returns fakeMovie

        // Then
        val result = getMovieDetailsUseCase(movieId)
        assertEquals(result, fakeMovie)

    }

}