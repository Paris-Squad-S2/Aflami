package com.domain.mediaDetails.useCases.tvShows

import com.domain.mediaDetails.repository.TvShowRepository
import com.domain.mediaDetails.useCase.tvShows.GetTvShowVideoUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import testUtils.fakeTvShowVideo

class GetTvShowVideoUseCaseTest {
    private lateinit var getTvShowVideoUseCase: GetTvShowVideoUseCase
    private val tvShowRepository: TvShowRepository = mockk(relaxed = true)

    @Before
    fun setup() {
        getTvShowVideoUseCase = GetTvShowVideoUseCase(tvShowRepository)
    }

    @Test
    fun `should return tv show video from repository`() = runTest {
        // Given
        val tvShowId = 1

        // when
        coEvery { tvShowRepository.getTrailerVideoForTvShow(tvShowId) } returns fakeTvShowVideo

        // Then
        val result = getTvShowVideoUseCase(tvShowId)
        assertEquals(result, fakeTvShowVideo.first())

    }

}