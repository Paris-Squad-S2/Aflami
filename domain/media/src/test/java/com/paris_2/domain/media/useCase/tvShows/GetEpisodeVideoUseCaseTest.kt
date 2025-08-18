package com.paris_2.domain.media.useCase.tvShows

import com.google.common.truth.Truth.assertThat
import com.paris_2.domain.media.repository.TvShowRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import com.paris_2.domain.media.testUtils.fakeMediaVideo

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
        } returns fakeMediaVideo

        // When
        val result = getEpisodeVideoUseCase(tvShowId,seasonNumber,episodeNumber)

        // Then
        assertThat(result).isEqualTo(fakeMediaVideo.first())
    }
}