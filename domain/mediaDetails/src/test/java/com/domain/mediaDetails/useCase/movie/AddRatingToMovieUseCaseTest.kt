package com.domain.mediaDetails.useCase.movie

import com.domain.mediaDetails.repository.TvShowRepository
import com.domain.mediaDetails.useCase.tvShows.AddRatingToTvShowUseCase
import io.mockk.coVerify
import io.mockk.coEvery
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
        coEvery { repository.addRatingToTvShow() } returns Unit

        // Act
        useCase()

        // Assert
        coVerify(exactly = 1) { repository.addRatingToTvShow() }
    }
}