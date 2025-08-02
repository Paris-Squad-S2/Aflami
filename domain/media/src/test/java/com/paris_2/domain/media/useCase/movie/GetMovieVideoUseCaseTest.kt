package com.paris_2.domain.media.useCase.movie

import com.paris_2.domain.media.repository.MovieRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import com.paris_2.domain.media.testUtils.fakeMovieVideo
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