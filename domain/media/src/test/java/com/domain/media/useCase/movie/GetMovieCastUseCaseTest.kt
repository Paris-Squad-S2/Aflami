package com.domain.media.useCase.movie

import com.domain.media.repository.MovieRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import com.domain.media.testUtils.fakeCast
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
    @Test
    fun `should throw exception when repository throws`() = runTest {
            // Given
            val exception = RuntimeException("Something went wrong")
            coEvery { movieRepository.getMovieCast(movieId) } throws exception

            // Then
            val thrown = kotlin.runCatching {
                getMovieCastUseCase(movieId)
            }.exceptionOrNull()

            assertEquals(exception, thrown)
        }


    private companion object{
        val movieId = 1
    }
}