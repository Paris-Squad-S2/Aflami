package com.paris.domain.media.useCase.tvShows

import com.paris.domain.media.repository.TvShowRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import com.paris.domain.media.testUtils.fakeCast
import com.google.common.truth.Truth.assertThat


class GetTvShowCastUseCaseTest {

    private lateinit var getTvShowCastUseCase: GetTvShowCastUseCase
    private val tvShowRepository: TvShowRepository = mockk(relaxed = true)

    @BeforeEach
    fun setup() {
        getTvShowCastUseCase = GetTvShowCastUseCase(tvShowRepository)
    }

    @Test
    fun `should return tv show cast from repository`() = runTest {
        // Given
        coEvery { tvShowRepository.getTvShowCast(tvShowId) } returns fakeCast

        // When
        val result = getTvShowCastUseCase(tvShowId)

        // Then
        assertThat(result).isEqualTo(fakeCast)
    }

    @Test
    fun `should call repository method to get cast`() = runTest {
        // Given
        coEvery { tvShowRepository.getTvShowCast(tvShowId) } returns fakeCast

        // When
        getTvShowCastUseCase(tvShowId)

        // Then
        coVerify(exactly = 1) { tvShowRepository.getTvShowCast(tvShowId) }
    }

    @Test
    fun `should return empty list when no cast found`() = runTest {
        // Given
        coEvery { tvShowRepository.getTvShowCast(tvShowId) } returns emptyList()

        // When
        val result = getTvShowCastUseCase(tvShowId)

        // Then
        assertThat(result).isEmpty()
    }

    @Test
    fun `should call repository method even if no cast found`() = runTest {
        // Given
        coEvery { tvShowRepository.getTvShowCast(tvShowId) } returns emptyList()

        // When
        getTvShowCastUseCase(tvShowId)

        // Then
        coVerify(exactly = 1) { tvShowRepository.getTvShowCast(tvShowId) }
    }

    private companion object {
        const val tvShowId = 1
    }
}
