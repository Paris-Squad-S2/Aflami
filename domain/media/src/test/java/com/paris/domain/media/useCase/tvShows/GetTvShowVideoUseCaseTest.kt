package com.paris.domain.media.useCase.tvShows

import com.google.common.truth.Truth.assertThat
import com.paris.domain.media.repository.TvShowRepository
import com.paris.domain.media.testUtils.fakeMediaVideo
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

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
        coEvery { tvShowRepository.getTrailerVideoForTvShow(tvShowId) } returns fakeMediaVideo

        // When
        val result = getTvShowVideoUseCase(tvShowId)

        // Then
        assertThat(result).isEqualTo(fakeMediaVideo.first())
    }
}
