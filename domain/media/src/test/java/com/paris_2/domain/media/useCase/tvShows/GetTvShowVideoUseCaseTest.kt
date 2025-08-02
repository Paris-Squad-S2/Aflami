package com.paris_2.domain.media.useCase.tvShows

import com.domain.media.repository.TvShowRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import com.domain.media.testUtils.fakeTvShowVideo

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
