package com.domain.mediaDetails.useCase.tvShows

import com.domain.mediaDetails.repository.TvShowRepository
import com.domain.mediaDetails.useCases.tvShows.GetTvShowVideoUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import testUtils.fakeTvShowVideo

class GetTvShowVideoUseCaseTest {

    private lateinit var getTvShowVideoUseCase: GetTvShowVideoUseCase
    private val tvShowRepository: TvShowRepository = mockk(relaxed = true)

    @BeforeEach
    fun setup() {
        getTvShowVideoUseCase = GetTvShowVideoUseCase(tvShowRepository)
    }

    @Test
    fun `should return tv show video from repository`() = runTest {
        // Given
        val tvShowId = 1
        coEvery { tvShowRepository.getTrailerVideoForTvShow(tvShowId) } returns fakeTvShowVideo

        // When
        val result = getTvShowVideoUseCase(tvShowId)

        // Then
        assertEquals(fakeTvShowVideo.first(), result)
    }
}
