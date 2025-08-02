package com.domain.mediaDetails.useCase.tvShows

import com.domain.media.repository.TvShowRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import testUtils.fakeEpisodeVideo

class GetEpisodeVideoUseCaseTest {

    private lateinit var getEpisodeVideoUseCase: GetEpisodeVideoUseCase
    private val tvShowRepository: TvShowRepository = mockk(relaxed = true)

    @BeforeEach
    fun setup() {
        getEpisodeVideoUseCase = GetEpisodeVideoUseCase(tvShowRepository)
    }

    @Test
    fun `should return episode video from repository`() = runTest {
        // Given
        val tvShowId = 1
        val seasonNumber = 1
        val episodeNumber = 1
        coEvery {
            tvShowRepository.getTrailerVideoForEpisode(
                tvShowId,
                seasonNumber,
                episodeNumber
            )
        } returns fakeEpisodeVideo

        // When
        val result = getEpisodeVideoUseCase(tvShowId,seasonNumber,episodeNumber)

        // Then
        assertEquals(fakeEpisodeVideo.first(), result)
    }
}