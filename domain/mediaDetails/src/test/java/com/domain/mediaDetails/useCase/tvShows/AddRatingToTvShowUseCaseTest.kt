package com.domain.mediaDetails.useCase.tvShows

import com.domain.mediaDetails.repository.TvShowRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class AddRatingToTvShowUseCaseTest {

    private lateinit var repository: TvShowRepository
    private lateinit var useCase: AddRatingToTvShowUseCase

    @BeforeEach
    fun setUp() {
        repository = mockk(relaxed = true)
        useCase = AddRatingToTvShowUseCase(repository)
    }

    @Test
    fun `invoke should call addRatingToTvShow on repository`() = runTest {
        // When
        useCase()

        // Then
        coVerify(exactly = 1) { repository.addRatingToTvShow() }
    }
}