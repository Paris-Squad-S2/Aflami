package com.domain.mediaDetails.useCase.movie

import com.domain.mediaDetails.repository.TvShowRepository
import com.domain.mediaDetails.useCase.tvShows.AddRatingToTvShowUseCase
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class AddRatingToMovieUseCaseTest {

    private lateinit var repository: TvShowRepository
    private lateinit var useCase: AddRatingToTvShowUseCase

    @BeforeEach
    fun setUp() {
        repository = mockk(relaxed = true)
        useCase = AddRatingToTvShowUseCase(repository)
    }

    @Test
    fun `invoke should call addRatingToTvShow on repository`() = runTest {
        // Arrange
        val movieId = 123
        val rating = 8.5f
        val sessionId = "dummy_session_id"

        // Act
        useCase(movieId, rating, sessionId)

        // Assert
        coVerify(exactly = 1) {
            repository.addRatingToTvShow(movieId, rating, sessionId)
        }
    }
}