package com.domain.mediaDetails.useCase.movie

import com.domain.mediaDetails.repository.MovieRepository
import com.domain.mediaDetails.useCases.movie.GetMovieVideoUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import testUtils.fakeMovieVideo
import kotlin.test.assertEquals

class GetMovieVideoUseCaseTest {
    private lateinit var getMovieVideoUseCase: GetMovieVideoUseCase
    private val movieRepository: MovieRepository = mockk(relaxed = true)

    @Before
    fun setup() {
        getMovieVideoUseCase = GetMovieVideoUseCase(movieRepository)
    }

    @Test
    fun `should return movie video from repository`() = runTest {
        // Given
        val movieId = 1

        // when
        coEvery { movieRepository.getTrailerVideoForMovie(movieId) } returns fakeMovieVideo

        // Then
        val result = getMovieVideoUseCase(movieId)
        assertEquals(result, fakeMovieVideo.first())

    }

}