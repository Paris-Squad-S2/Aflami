package com.domain.mediaDetails.useCase.movie

import com.domain.mediaDetails.repository.MovieRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import testUtils.fakeCast
import kotlin.test.Test
import kotlin.test.assertEquals


class GetMovieCastUseCaseTest {
    private lateinit var getMovieCastUseCase: GetMovieCastUseCase
    private val movieRepository: MovieRepository = mockk(relaxed = true)

    @BeforeEach
    fun setup() {
        getMovieCastUseCase = GetMovieCastUseCase(movieRepository)
    }

    @Test
    fun `should return movie cast from repository`() = runTest {
        // Given
        coEvery { movieRepository.getMovieCast(movieId) } returns fakeCast

        //When
        val result = getMovieCastUseCase(movieId)

        // Then
        assertEquals(result, fakeCast)

    }

    @Test
    fun `should verify repository interaction when getting cast`() = runTest {
        // Given
        coEvery { movieRepository.getMovieCast(movieId) } returns fakeCast

        // When
        getMovieCastUseCase(movieId)

        // Then
        coVerify(exactly = 1) { movieRepository.getMovieCast(movieId) }
    }

    @Test
    fun `should return empty list when no cast found`() = runTest{
        // Given
        coEvery { movieRepository.getMovieCast(movieId) } returns emptyList()

        // when
        val result = getMovieCastUseCase(movieId)

        // Then
        assertEquals(result, emptyList())
    }

    @Test
    fun `should verify repository interaction when no cast is found`() = runTest {
        // Given
        coEvery { movieRepository.getMovieCast(movieId) } returns emptyList()

        // When
        getMovieCastUseCase(movieId)

        // Then
        coVerify(exactly = 1) { movieRepository.getMovieCast(movieId) }
    }

    private companion object{
        val movieId = 1
    }
}